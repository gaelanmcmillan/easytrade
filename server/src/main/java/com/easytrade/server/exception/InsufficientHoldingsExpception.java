package com.easytrade.server.exception;

public class InsufficientHoldingsExpception extends Exception {
    public InsufficientHoldingsExpception(String symbol) {
        super(String.format("User does not have sufficient holdings in '%s'", symbol));
    }
}
