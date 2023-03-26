package com.easytrade.server.model;


import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.sql.Date;

/**
 * This class creates a composite ID out of a Stock and a Date,
 * ensuring we only ever store a single slice of stock data per stock per day.
 * */
public class StockDataId implements Serializable {
    @ManyToOne
    @JoinColumn(name="ticker_symbol")
    private Stock stock;
    private Date date;
}
