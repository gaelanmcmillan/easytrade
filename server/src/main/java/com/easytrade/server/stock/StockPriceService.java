package com.easytrade.server.stock;

import com.easytrade.server.config.EasyTradeProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The <code>StockPriceService</code> is responsible for fetching stock data from
 * the YahooFinanceAPI and storing it in our database.
 * */
@Service
@RequiredArgsConstructor
public class StockPriceService {
    private final EasyTradeProperties easyTradeProperties;
    private final StockPriceRepository stockPriceRepository;



}
