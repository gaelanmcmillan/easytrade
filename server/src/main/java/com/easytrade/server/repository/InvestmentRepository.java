package com.easytrade.server.repository;

import com.easytrade.server.model.Investment;
import com.easytrade.server.model.InvestmentId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface InvestmentRepository extends CrudRepository<Investment, InvestmentId> {
    @Query("SELECT holding FROM Investment holding" +
            " WHERE holding.user.username = :username")
    List<Investment> getUserStockHoldingsByUsername(String username);

    @Query("SELECT holding from Investment holding" +
            " WHERE holding.user.username = :username AND holding.stock.symbol = :symbol")
    Optional<Investment> getUserStockHoldingBySymbol(String username, String symbol);
}
