package com.easytrade.server.service;

import com.easytrade.server.config.EasyTradeProperties;
import com.easytrade.server.repository.StockDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The <code>StockPriceService</code> is responsible for fetching stock data from
 * the YahooFinanceAPI and storing it in our database.
 * */
@Service
@RequiredArgsConstructor
public class StockDataService {
    private final EasyTradeProperties easyTradeProperties;
    private final StockDataRepository stockPriceRepository;



}
