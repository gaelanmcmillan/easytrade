package com.easytrade.server.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface StockPriceRepository extends JpaRepository<StockPrice, Integer> {
    @Query("SELECT p FROM StockPrice p WHERE p.stock.symbol = :symbol AND p.date = :date")
    StockPrice getPriceBySymbolAndDate(String symbol, LocalDate date);

    @Query("SELECT p FROM StockPrice p WHERE p.stock.symbol = :symbol AND (p.date >= :start AND p.date <= :end)")
    List<StockPrice> getPricesBySymbolBetweenDates(String symbol, LocalDate start, LocalDate end);
}
