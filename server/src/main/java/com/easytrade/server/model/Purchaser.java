package com.easytrade.server.model;

import com.easytrade.server.exception.InsufficientFundsException;

import java.math.BigDecimal;

public interface Purchaser {
    public void makePurchase(BigDecimal amount) throws InsufficientFundsException;
}
