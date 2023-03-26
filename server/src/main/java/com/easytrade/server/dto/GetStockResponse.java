package com.easytrade.server.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetStockResponse {
    String symbol;
    String company;
    Date date;
    BigDecimal open;
    BigDecimal close;
    BigDecimal high;
    BigDecimal low;
}
