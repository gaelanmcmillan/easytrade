package com.easytrade.server.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Stores a slice of time-series data regarding the historical price of a stock
 * */
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
    private Date date;
    private BigDecimal ask;
    private BigDecimal bid;
    private BigDecimal open;
    private BigDecimal close;
    private BigDecimal price;
    private Long volume;


//    private BigDecimal earningsPerShare;
//    private BigDecimal priceToEarningsRatio;
}
