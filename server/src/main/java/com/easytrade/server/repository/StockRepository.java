package com.easytrade.server.repository;

import com.easytrade.server.model.Stock;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StockRepository extends CrudRepository<Stock, String> {
    Optional<Stock> getStockBySymbol(String symbol);
}
