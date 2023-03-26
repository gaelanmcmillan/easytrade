package com.easytrade.server.dto;

import com.easytrade.server.model.Stock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllStocksResponse {
    List<Stock> stocks;
}

/**
 * Example response body:
 * {
 *     "stocks": [
 *         {
 *             "symbol": "AAPL",
 *             "company": "Apple Inc",
 *             "currentOpen": 158.86,
 *             "changeInPercent": 0.83
 *         },
 *         {
 *             "symbol": "GOOG",
 *             "company": "Google",
 *             "currentOpen": 105.74,
 *             "changeInPercent": -0.19
 *         },
 *         {
 *             "symbol": "GOOGL",
 *             "company": "todo",
 *             "currentOpen": 104.99,
 *             "changeInPercent": -0.15
 *         },
 *         {
 *             "symbol": "META",
 *             "company": "todo",
 *             "currentOpen": 205.18,
 *             "changeInPercent": 0.85
 *         },
 *         {
 *             "symbol": "MSFT",
 *             "company": "todo",
 *             "currentOpen": 277.24,
 *             "changeInPercent": 1.05
 *         },
 *         {
 *             "symbol": "NVDA",
 *             "company": "todo",
 *             "currentOpen": 270.31,
 *             "changeInPercent": -1.52
 *         },
 *         {
 *             "symbol": "PCAR",
 *             "company": "todo",
 *             "currentOpen": 69.26,
 *             "changeInPercent": 0.13
 *         },
 *         {
 *             "symbol": "TSLA",
 *             "company": "todo",
 *             "currentOpen": 191.65,
 *             "changeInPercent": -0.94
 *         }
 *     ]
 * }
 * */