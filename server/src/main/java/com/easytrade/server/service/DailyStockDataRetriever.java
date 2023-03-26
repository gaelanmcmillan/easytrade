package com.easytrade.server.service;

import com.easytrade.server.config.EasyTradeProperties;
import com.easytrade.server.model.Stock;
import com.easytrade.server.model.StockData;
import com.easytrade.server.repository.StockDataRepository;
import com.easytrade.server.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.stock.StockQuote;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DailyStockDataRetriever {
    private final StockDataRepository stockDataRepository;
    private final StockRepository stockRepository;
    private final EasyTradeProperties easyTradeProperties;

    /**
     * This is a scheduled task that retrieves the daily stock data for each stock,
     * ensuring EasyTrade always has up-to-date data.
     * */
    @Transactional
    @Scheduled(fixedRate=864_000_000) // 24 hours (in millis)
    public void retrieveDailyStockData() {

        // Since this task is attempted at our server's startup,
        // we must check that we're not duplicating the work of retrieving
        // our daily stock data.
        if (isStockDataUpToDate()) {
            System.out.println("STOCK DATA IS UP TO DATE");
            return;
        }

        String[] symbols = easyTradeProperties.getSymbols();
        Map<String, yahoofinance.Stock> yahooFinanceStockMap;
        try {
            yahooFinanceStockMap = YahooFinance.get(symbols);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }

        // TODO: Refactor this into a couple functions...
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

                            updateClosePriceFromYesterday(symbol, stockQuote.getPreviousClose());

                            return StockData.builder()
                                    .stock(stock)
                                    .ask(stockQuote.getAsk())
                                    .bid(stockQuote.getBid())
                                    .open(stockQuote.getOpen())
                                    .price(stockQuote.getPrice())
                                    .date(Date.valueOf(LocalDate.from(LocalDate.now().atStartOfDay())))
                                    .build();
                        }).toList();

        // Save all data points from today
        stockDataRepository.saveAll(dataFromToday);
    }

    private boolean isStockDataUpToDate() {
        System.out.println("CHECKING IF STOCK DATA IS UP TO DATE");
        String[] symbols = easyTradeProperties.getSymbols();
        Date today = Date.valueOf(LocalDate.from(LocalDate.now().atStartOfDay()));
        List<StockData> dataFromToday = stockDataRepository.getAllSymbolsBetweenDates(today, today);
        HashSet<String> symbolsAccountedFor = (HashSet<String>) dataFromToday.stream().map(data -> data.getStock().getSymbol()).collect(Collectors.toSet());
        System.out.printf("FOUND %d SYMBOLS FROM TODAY: %s\n", symbolsAccountedFor.size(), String.join(", ", symbolsAccountedFor));
        dataFromToday.forEach(data -> System.out.println(data.toString()));

        return Arrays.stream(symbols).allMatch(symbolsAccountedFor::contains);
    }


    private void updateClosePriceFromYesterday(String tickerSymbol, BigDecimal close) {
        Date yesterday = Date.valueOf(LocalDate.from(LocalDate.now().atStartOfDay().minusDays(1)));
        Optional<StockData> dataFromYesterday = stockDataRepository.getPriceBySymbolAndDate(tickerSymbol, yesterday);
        dataFromYesterday.ifPresent(stockData -> stockData.setClose(close));
        stockDataRepository.save(dataFromYesterday.get());
    }
}
