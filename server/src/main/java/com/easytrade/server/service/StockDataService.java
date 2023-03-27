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
}
