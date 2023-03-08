package com.easytrade.server.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

//    @Query(value = "SELECT * FROM token WHERE ")
    List<Token> findAllValidByUserId(Integer userId);

    Optional<Token> findByToken(String token);

}