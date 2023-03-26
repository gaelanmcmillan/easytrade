package com.easytrade.server.dto;

import com.easytrade.server.model.Stock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvestmentDTO {
    Stock stock;
    Long quantity;
}
