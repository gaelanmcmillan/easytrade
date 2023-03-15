package com.easytrade.server.repository;

import com.easytrade.server.model.Portfolio;
import org.springframework.data.repository.CrudRepository;

public interface PortfolioRepository extends CrudRepository<Portfolio, Integer> {
//    @Query("SELECT p FROM Portfolio p INNER JOIN User u ON p.user.id")
//    Optional<Portfolio> findByUsername (String email);
}
