package org.example;

import java.io.IOException;
import yahoofinance.YahooFinance;
import yahoofinance.Stock;

public class DataScraper {
    Stock getStock(String name) {
        try {
            return YahooFinance.get(name);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
