package com.easytrade.server.dto.stock;

import com.easytrade.server.model.StockData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockDataListResponse {
    List<StockData> stockData;
}
