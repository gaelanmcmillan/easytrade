package com.easytrade.server.exception;

public class AccountWithUsernameExistsException extends Exception {
    public AccountWithUsernameExistsException (String message) {
        super(message);
    }
}
