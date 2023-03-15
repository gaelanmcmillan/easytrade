package com.easytrade.server.stock;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface StockDataRepository extends CrudRepository<StockData, Integer> {
    @Query("SELECT p FROM StockData p WHERE p.stock.symbol = :symbol AND p.date = :date")
    Optional<StockData> getPriceBySymbolAndDate(String symbol, LocalDate date);

    @Query("SELECT p FROM StockData p WHERE p.stock.symbol = :symbol AND (p.date >= :start AND p.date <= :end)")
    List<StockData> getPricesBySymbolBetweenDates(String symbol, LocalDate start, LocalDate end);
}
