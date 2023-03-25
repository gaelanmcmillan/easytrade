package com.easytrade.server.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix="easytrade")
public class EasyTradeProperties {
    private String exchange;
    private String[] symbols;

    @Value("${easytrade.starting-balance}")
    private BigDecimal startingBalance;
}
