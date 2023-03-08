package com.easytrade.server.stock;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="prices")
public class StockPrice {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name="stock_id")
    private Stock stock;

    @Column(name="date")
    private LocalDate date;

    @Column(name="price")
    private BigInteger price;

    @Enumerated(EnumType.STRING)
    private PriceType priceType;
}
