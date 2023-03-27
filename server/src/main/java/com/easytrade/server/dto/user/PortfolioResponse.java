package com.easytrade.server.dto.user;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioResponse {
    String username;
    List<InvestmentDTO> investments;
}
