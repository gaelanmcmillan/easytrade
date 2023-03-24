package com.easytrade.server.exception;

public class UnknownTickerSymbolException extends Exception {
    public UnknownTickerSymbolException(String message) {
        super(message);
    }
}
