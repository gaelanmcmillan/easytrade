package com.easytrade.server.service;

import com.easytrade.server.dto.BuyStockRequest;
import com.easytrade.server.dto.BuyStockResponse;
import com.easytrade.server.exception.InsufficientFundsException;
import com.easytrade.server.exception.InvalidQuantityException;
import com.easytrade.server.exception.NonexistentUserException;
import com.easytrade.server.exception.UnknownTickerSymbolException;
import com.easytrade.server.model.User;
import com.easytrade.server.repository.StockDataRepository;
import com.easytrade.server.repository.StockRepository;
import com.easytrade.server.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
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



    public BuyStockResponse buyStock(String bearerToken, BuyStockRequest request)
            throws  NonexistentUserException,
                    InvalidQuantityException,
                    UnknownTickerSymbolException,
                    InsufficientFundsException
    {
        String tokenLiteral = bearerToken.substring("Bearer ".length());
        String username = jwtService.extractUsername(tokenLiteral);

        // Error case: Token is expired (I think this will fail before reaching us)
//        if (jwtService.isTokenExpired(tokenLiteral)) {
//            // Using regular exception because I'm not sure this check is necessary
//            throw new Exception("Token is expired");
//        }

        // Error case: User doesn't exist
        User user = userRepository.findByUsername(username)
                                  .orElseThrow(() -> new NonexistentUserException(
                                          String.format("User with username '%s' doesn't exist", username)));

        // Error case: Quantity is invalid
        int quantity = request.getQuantity();
        if (quantity < 0) {
            throw new InvalidQuantityException(
                    String.format(
                            "Quantity '%d' is not valid. Quantity should be a positive integer.", quantity));
        }

        // Error case: Ticker symbol doesn't exist
        String tickerSymbol = request.getSymbol();
        BigDecimal price = stockDataService.getLatestPrice(tickerSymbol);

        // Error case: Insufficient funds (throws InsufficientFundsError)
        user.makePurchase(price.multiply(BigDecimal.valueOf(quantity)));

        return BuyStockResponse.builder().message("Success").build();
    }
}
