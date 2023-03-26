package com.easytrade.server.dto;

import com.easytrade.server.model.Stock;
import lombok.*;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioResponse {
    String username;
    List<InvestmentDTO> investments;
}
