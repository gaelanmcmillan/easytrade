package com.easytrade.server.service;

import com.easytrade.server.dto.BuyStockRequest;
import com.easytrade.server.exception.InsufficientFundsException;
import com.easytrade.server.exception.UnknownTickerSymbolException;
import com.easytrade.server.model.User;
import com.easytrade.server.repository.StockDataRepository;
import com.easytrade.server.repository.StockRepository;
import com.easytrade.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockMarketService {
    private final UserRepository userRepository;
    private final StockRepository stockRepository;
    private final StockDataRepository stockDataRepository;
    private final StockDataService stockDataService;

    private final JsonWebTokenService jwtService;

    public ResponseEntity<?> buyStock(String bearerToken, BuyStockRequest request) {
        String tokenLiteral = bearerToken.substring("Bearer ".length());
        String username = jwtService.extractUsername(tokenLiteral);

        // Error case: Token is expired
        if (jwtService.isTokenExpired(tokenLiteral)) {
            return ResponseEntity.badRequest().body("Token is expired...");
        }

        // Error case: User doesn't exist
        Optional<User> maybeUser = userRepository.findByUsername(username);

        if (maybeUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Bad credentials...");
        }
        User user = maybeUser.get();


        // Error case: Quantity is invalid
        int quantity = request.getQuantity();
        if (quantity < 0) {
            return ResponseEntity.badRequest().body("Invalid quantity. Must be a positive integer.");
        }

        // Error case: Ticker symbol doesn't exist
        String tickerSymbol = request.getSymbol();

        BigDecimal price;
        try {
            price = stockDataService.getLatestPrice(tickerSymbol);
        } catch (UnknownTickerSymbolException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        // Error case: Insufficient funds
        try {
            user.makePurchase(price.multiply(BigDecimal.valueOf(quantity)));
        } catch (InsufficientFundsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(username);
    }
}
