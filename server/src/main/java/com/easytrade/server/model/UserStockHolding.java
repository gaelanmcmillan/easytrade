package com.easytrade.server.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * A UserStockHolding represents a given User's stake in a stock
 * */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(UserStockHoldingId.class)
public class UserStockHolding {
    @Id
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name="ticker_symbol")
    private Stock stock;

    // The number of shares a user holds of a given stock.
    private long quantity;
}
