package com.easytrade.server.repository;

import com.easytrade.server.model.Stock;
import org.springframework.data.repository.CrudRepository;

public interface StockRepository extends CrudRepository<Stock, String> {
}
