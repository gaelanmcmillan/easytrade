package com.easytrade.server.dto.stock;

import lombok.Data;

/**
 * GetStockRequest represents the structure of the JSON data accepted by
 * requests to `GET` <api-prefix>/stock
 * */
@Data
public class SingleStockRequest {
    private String symbol;
}
