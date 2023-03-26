package com.easytrade.server.service;

import com.easytrade.server.dto.*;
import com.easytrade.server.exception.*;
import com.easytrade.server.model.*;
import com.easytrade.server.repository.StockDataRepository;
import com.easytrade.server.repository.StockRepository;
import com.easytrade.server.repository.UserRepository;
import com.easytrade.server.repository.UserStockHoldingRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockMarketService {
    private final UserRepository userRepository;
    private final UserStockHoldingRepository userStockHoldingRepository;
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

        BigDecimal costOfPurchase = price.multiply(BigDecimal.valueOf(quantity));

        // Error case: Insufficient funds (throws InsufficientFundsError)
        user.makePurchase(costOfPurchase);

        // TODO: Could save a transaction here

        // Update user's holding in the stock
        UserStockHolding userStockHolding = userStockHoldingRepository.getUserStockHoldingBySymbol(username, tickerSymbol)
                .orElse(UserStockHolding.builder()
                        .user(user)
                        .stock(stockRepository.getStockBySymbol(tickerSymbol).get()) // It's verified that the stock exists
                        .quantity(0)
                        .build());

        userStockHolding.setQuantity(userStockHolding.getQuantity() + quantity);
        userStockHoldingRepository.save(userStockHolding);

        return BuyStockResponse.builder().message("Success").build();
    }

    public void sellStock(String bearerToken, SellStockRequest request) throws NonexistentUserException, InvalidQuantityException, UnknownTickerSymbolException, InsufficientFundsException, InsufficientHoldingsExpception {
        String tokenLiteral = bearerToken.substring("Bearer ".length());
        String username = jwtService.extractUsername(tokenLiteral);

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

        // Error case: User doesn't hold enough of the given stock
        Optional<UserStockHolding> maybeHolding = userStockHoldingRepository.getUserStockHoldingBySymbol(username, tickerSymbol);

        if (maybeHolding.isEmpty() || maybeHolding.get().getQuantity() < quantity) {
            throw new InsufficientHoldingsExpception(tickerSymbol);
        }

        UserStockHolding userStockHolding = maybeHolding.get();

        BigDecimal earningsFromSale = price.multiply(BigDecimal.valueOf(quantity));

        user.setAccountBalance(user.getAccountBalance().add(earningsFromSale));

        // TODO: Could save a transaction here

        // Update user's holding in the stock
        userStockHolding.setQuantity(userStockHolding.getQuantity() - quantity);
        userStockHoldingRepository.save(userStockHolding);
    }

    public AllStocksResponse getAllStocks() {
        List<Stock> stocks = (List<Stock>) stockRepository.findAll();
        return AllStocksResponse.builder().stocks(stocks).build();
    }


    public GetStockResponse getStock(GetStockRequest request) throws UnknownTickerSymbolException {
        String symbol = request.getSymbol();
        StockData stockData = stockDataRepository.getPriceBySymbolAndDate(symbol, Date.valueOf(LocalDate.from(LocalDate.now().atStartOfDay())))
                .orElseThrow(() -> new UnknownTickerSymbolException(symbol));

        return GetStockResponse.builder().symbol(symbol).build();
    }


}
