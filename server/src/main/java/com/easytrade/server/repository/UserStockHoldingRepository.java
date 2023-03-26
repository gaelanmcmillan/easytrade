package com.easytrade.server.repository;

import com.easytrade.server.model.UserStockHolding;
import com.easytrade.server.model.UserStockHoldingId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserStockHoldingRepository extends CrudRepository<UserStockHolding, UserStockHoldingId> {
    @Query("SELECT holding FROM UserStockHolding holding" +
            " WHERE holding.user.username = :username")
    List<UserStockHolding> getUserStockHoldingsByUsername(String username);

    @Query("SELECT holding from UserStockHolding holding" +
            " WHERE holding.user.username = :username AND holding.stock.symbol = :symbol")
    Optional<UserStockHolding> getUserStockHoldingBySymbol(String username, String symbol);
}
