package com.easytrade.server.repository;

import com.easytrade.server.model.StockData;
import com.easytrade.server.model.StockDataId;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StockDataRepository extends CrudRepository<StockData, StockDataId> {
    @Query("SELECT p FROM StockData p WHERE p.stock.symbol = :symbol AND p.date = :date")
    Optional<StockData> getPriceBySymbolAndDate(String symbol, LocalDate date);

    @Query("SELECT p FROM StockData p WHERE (p.date >= :start AND p.date <= :end)")
    List<StockData> getAllSymbolsBetweenDates(Date start, Date end);

    @Query("SELECT p FROM StockData p WHERE p.stock.symbol = :symbol AND (p.date >= :start AND p.date <= :end)")
    List<StockData> getPricesBySymbolBetweenDates(String symbol, LocalDate start, LocalDate end);
}
