package com.easytrade.server.exception;

import java.sql.Date;

public class InvalidDateException extends Exception {
    public InvalidDateException(Date date) {
        super(String.format("Invalid date: '%s'", date.toString()));
    }
}
