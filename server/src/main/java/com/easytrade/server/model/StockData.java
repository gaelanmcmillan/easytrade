package com.easytrade.server.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="prices")
public class StockData {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name="stock_id")
    private Stock stock;

    @Column(name="date")
    private LocalDate date;

    private BigDecimal ask;
    private BigDecimal bid;
    private BigDecimal price;
    private BigDecimal previousClose;
    private BigDecimal earningsPerShare;
    private BigDecimal priceToEarningsRatio;


    @Enumerated(EnumType.STRING)
    private PriceType priceType;
}
