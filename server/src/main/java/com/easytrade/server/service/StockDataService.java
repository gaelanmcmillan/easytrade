package com.easytrade.server.service;

import com.easytrade.server.config.EasyTradeProperties;
import com.easytrade.server.exception.InvalidDateException;
import com.easytrade.server.exception.UnknownTickerSymbolException;
import com.easytrade.server.model.Stock;
import com.easytrade.server.model.StockData;
import com.easytrade.server.repository.StockDataRepository;
import com.easytrade.server.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * The <code>StockPriceService</code> is responsible for fetching stock data from
 * the YahooFinanceAPI and storing it in our database.
 * */
@Service
@RequiredArgsConstructor
public class StockDataService {
    private final EasyTradeProperties easyTradeProperties;
    private final StockDataRepository stockDataRepository;
    private final StockRepository stockRepository;

    private void updateAllStocks() {
        var stockSymbols = easyTradeProperties.getSymbols();
    }
    private Boolean shouldUpdateStock(Stock stock) {
        return true;
    }

    // Now the latest stock data is fresh
    private StockData updateStockDataFor(Stock stock) {
        return null;
    }

    public BigDecimal getLatestPrice(String tickerSymbol) throws UnknownTickerSymbolException {
        Stock stock = stockRepository.getStockBySymbol(tickerSymbol).orElseThrow(
                () -> new UnknownTickerSymbolException(tickerSymbol));

        return stock.getCurrentOpen();
    }

    /**
     * Success cases:
     * --------------
     * (1) Full: Database contains stock data for all days in [from, to].
     * (2) Left-partial: Database contains stock data for [d0, to] for from < d0 <= to
     * (3) Right-partial: Database contains stock data for [from, d1] for from >= d1 > to
     * (4) Patchy: Database contains stock data for 1 or more sub-intervals inside (from, to).
     * (5) None: Database contains no data from [from, to].
     *
     * Error cases:
     * ------------
     * (1) Invalid dates
     * */
    private List<StockData> retrieveStockDataInterval(String symbol, Date from, Date to) throws InvalidDateException, IOException, UnknownTickerSymbolException {
        // Handle error case: Stock doesn't exist
        Stock stock = stockRepository.getStockBySymbol(symbol).orElseThrow(() -> new UnknownTickerSymbolException(symbol));
        // Handle error case: Invalid dates
        Date today = Date.valueOf(LocalDate.from(LocalDate.now().atStartOfDay()));

        if (to.before(from) || today.before(from)) {
            throw new InvalidDateException(from);
        }
        if (today.before(to)) {
            throw new InvalidDateException(to);
        }

        long millisOffsetFrom = today.getTime() - from.getTime();
        long millisOffsetTo = today.getTime() - to.getTime();

        long intervalWidthInDays = ChronoUnit.DAYS.between((Temporal) from, (Temporal) to);

        System.out.printf("INTERVAL WIDTH IN DAYS: '%d'\n", intervalWidthInDays);

        Calendar calendarFrom = Calendar.getInstance();
        Calendar calendarTo = Calendar.getInstance();

        calendarFrom.add(Calendar.MILLISECOND, - (int) millisOffsetFrom);
        calendarTo.add(Calendar.MILLISECOND, - (int) millisOffsetTo);

        // Handle (1) Full
        {
            List<StockData> stockDataList = stockDataRepository.getPricesBySymbolBetweenDates(symbol, from, to);
        }
        // Handle (2) Left-partial
        // Handle (3) Right-partial

        // Handle (4) Patchy and (5) None
        // Solution: Make a request to YahooFinance for [from, to]
        {
            yahoofinance.Stock yahooStockData = YahooFinance.get(symbol, calendarFrom, calendarTo, Interval.DAILY);
            List<StockData> stockDataList = yahooStockData.getHistory().stream().map(historicalQuote -> {
                LocalDate asLocalDate = LocalDate.from((TemporalAccessor) historicalQuote.getDate());
                Date date = Date.valueOf(LocalDate.from(asLocalDate.atStartOfDay()));

                // TODO: Abstract this to StockData.fromHistoricalQuote
                return StockData.builder()
                        .stock(stock)
                        .date(date)
                        .open(historicalQuote.getOpen())
                        .close(historicalQuote.getClose())
                        .adjClose(historicalQuote.getAdjClose())
                        .high(historicalQuote.getHigh())
                        .low(historicalQuote.getLow())
                        .volume(historicalQuote.getVolume())
                        .build();
            }).toList();

            stockDataRepository.saveAll(stockDataList);

            return stockDataList;
        }
    }
}
