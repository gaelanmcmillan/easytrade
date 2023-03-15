package com.easytrade.server.portfolio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PortfolioRepository extends CrudRepository<Portfolio, Integer> {
//    @Query("SELECT p FROM Portfolio p INNER JOIN User u ON p.user.id")
//    Optional<Portfolio> findByUsername (String email);
}
