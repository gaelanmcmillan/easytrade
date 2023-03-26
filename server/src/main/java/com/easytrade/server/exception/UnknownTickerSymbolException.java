package com.easytrade.server.exception;

public class UnknownTickerSymbolException extends Exception {
    public UnknownTickerSymbolException(String symbol) {
        super(String.format("Stock with ticker symbol '%s' does not exist in our system.", symbol));
    }
}
