package com.easytrade.server.dto;

import lombok.Data;

/**
 * GetStockRequest represents the structure of the JSON data accepted by
 * requests to `GET` <api-prefix>/stock
 * */
@Data
public class GetStockRequest {
    private String symbol;
}
