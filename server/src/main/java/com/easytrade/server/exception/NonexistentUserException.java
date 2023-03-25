package com.easytrade.server.exception;

public class NonexistentUserException extends Exception {
    public NonexistentUserException(String message) {
        super(message);
    }
}
