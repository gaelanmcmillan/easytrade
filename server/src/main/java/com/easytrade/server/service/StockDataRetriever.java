package com.easytrade.server.service;

import com.easytrade.server.config.EasyTradeProperties;
import com.easytrade.server.model.Stock;
import com.easytrade.server.model.StockData;
import com.easytrade.server.repository.StockDataRepository;
import com.easytrade.server.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.stock.StockQuote;

import java.io.IOException;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StockDataRetriever {
    private final StockDataRepository stockDataRepository;
    private final StockRepository stockRepository;
    private final EasyTradeProperties easyTradeProperties;

    @Scheduled(fixedRate=864_000_000) // 24 hours (in millis)
    public void retrieveStockData() {
        String[] symbols = easyTradeProperties.getSymbols();
        Map<String, yahoofinance.Stock> yahooFinanceStockMap;
        try {
            yahooFinanceStockMap = YahooFinance.get(symbols);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }

        List<StockData> dataFromToday = yahooFinanceStockMap.entrySet().stream()
                        .map((entry) -> {
                            String symbol = entry.getKey();
                            yahoofinance.Stock yahooFinanceStock = entry.getValue();
                            StockQuote stockQuote = yahooFinanceStock.getQuote();

                            // TODO: This fallback potentially saves data twice.
                            Stock stock = stockRepository.getStockBySymbol(symbol).orElse(
                                    stockRepository.save(Stock.builder()
                                            .symbol(symbol)
                                            .company("todo")
                                            .currentOpen(stockQuote.getOpen())
                                            .changeInPercent(stockQuote.getChangeInPercent())
                                            .build()));

                            // If the stock was already present, we still want to update its data.
                            stock.setCurrentOpen(stockQuote.getOpen());
                            stock.setChangeInPercent(stockQuote.getChangeInPercent());
                            stockRepository.save(stock);

                            return StockData.builder()
                                    .stock(stock)
                                    .ask(stockQuote.getAsk())
                                    .bid(stockQuote.getBid())
                                    .open(stockQuote.getOpen())
                                    .price(stockQuote.getPrice())
                                    .date(Date.valueOf(LocalDate.now()))
                                    .build();
                        }).toList();

        // Save all data points from today
        stockDataRepository.saveAll(dataFromToday);

        yahooFinanceStockMap.forEach((symbol, stock) -> {
            System.out.printf("Stock: '%s'\n" +
                            "Data: '%s'\n" +
                            "Quote: '%s'\n" +
                            "Stats: '%s'\n" +
                            "Dividend: '%s'\n" +
                            "Short Ratio: '%s'\n" +
                            "Open: '%s'\n" +
                            "Testing percentage thing: '%s'\n" +
                            "\n",
                    symbol,
                    stock.toString(),
                    stock.getQuote().toString(),
                    stock.getStats().toString(),
                    stock.getDividend().toString(),
                    stock.getStats().getShortRatio(),
                    stock.getQuote().getOpen(),
                    stock.getQuote().getChangeInPercent()
//                    (stock.getQuote().getOpen().divide(stock.getQuote().getPreviousClose(), RoundingMode.HALF_UP))
        );
        });
    }
}
