package com.easytrade.server.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;


public class InvestmentId implements Serializable {

    @ManyToOne
    @JoinColumn(name="user_id")
    User user;

    @ManyToOne
    @JoinColumn(name="ticker_symbol")
    Stock stock;
}

