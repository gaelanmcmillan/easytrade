package com.easytrade.server.service;

import com.easytrade.server.config.EasyTradeProperties;
import com.easytrade.server.exception.UnknownTickerSymbolException;
import com.easytrade.server.model.Stock;
import com.easytrade.server.model.StockData;
import com.easytrade.server.repository.StockDataRepository;
import com.easytrade.server.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
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
                () -> new UnknownTickerSymbolException("No stock with symbol '" + tickerSymbol + "' could be found."));
        StockData data;
        if (shouldUpdateStock(stock)) {
            data = updateStockDataFor(stock);
        } else {
            data = stockDataRepository.getPriceBySymbolAndDate(tickerSymbol, LocalDate.now()).get();
        }

        return data.getPrice();
    }
}
