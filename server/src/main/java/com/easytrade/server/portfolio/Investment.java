package com.easytrade.server.portfolio;

import com.easytrade.server.stock.Stock;
import com.easytrade.server.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * An <code>Investment</code> represents a user's stake in a particular company.
 * It consists of a particular <code>Stock</code> and a count, representing the number of units the user holds.
 * */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Investment {
    @Id
    @GeneratedValue
    private Integer id;

    // Having both of these many-to-one relationships seems unnecessary
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="portfolio_id")
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name="stock_symbol")
    private Stock stock;

    private int stockCount;
}
