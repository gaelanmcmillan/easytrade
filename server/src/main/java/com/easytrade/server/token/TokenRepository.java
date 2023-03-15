package com.easytrade.server.token;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface TokenRepository extends CrudRepository<Token, Integer> {

    @Query(value = "SELECT t FROM Token t INNER JOIN User u " +
                   "ON t.user.id = u.id " +
                   "WHERE u.id = :userId AND (t.expired = FALSE OR t.revoked = FALSE)")
    List<Token> findAllValidByUserId(Integer userId);

    Optional<Token> findByLiteral(String literal);

}
