package com.easytrade.server.controller;

import com.easytrade.server.dto.stock.BuyStockRequest;
import com.easytrade.server.dto.stock.SingleStockRequest;
import com.easytrade.server.dto.stock.SellStockRequest;
import com.easytrade.server.exception.InsufficientFundsException;
import com.easytrade.server.exception.InvalidQuantityException;
import com.easytrade.server.exception.NonexistentUserException;
import com.easytrade.server.exception.UnknownTickerSymbolException;
import com.easytrade.server.service.StockMarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class StockMarketController {
    private final StockMarketService stockMarketService;
    /**
     * Handle a stock purchase request.
     * Preconditions
     * -------------
     *  (1) User exists
     *  (2) Stock exists
     *  (3) User has sufficient funds for purchase
     *
     * Post-conditions
     * ---------------
     * Success
     *  (1) Funds have been deducted from user's bank account
     *  (2) Additional shares of selected stock have been added to user's portfolio
     *  (3) HTTP NO CONTENT (204) is delivered to client
     *
     * Failure
     *  (1) HTTP BAD REQUEST (400) is delivered to client
     *  */
    @PatchMapping("/buy")
    @Transactional
    public ResponseEntity<?> buyStock(
            @RequestHeader (HttpHeaders.AUTHORIZATION) String bearerToken,
            @RequestBody BuyStockRequest request) {
        try {
            stockMarketService.buyStock(bearerToken, request);
        } catch (NonexistentUserException | InvalidQuantityException | UnknownTickerSymbolException |
                 InsufficientFundsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Handle a stock sale request.
     * Preconditions
     * -------------
     *  (1) User exists
     *  (2) Stock exists
     *  (3) User has sufficient stocks to sell
     *
     * Post-conditions
     * ---------------
     * Success
     *  (1) Funds have been added to user's account balance
     *  (2) Additional shares of selected stock have been removed from user's portfolio
     *  (3) HTTP NO CONTENT (204) is delivered to client
     *
     * Failure
     *  (1) HTTP BAD REQUEST (400) is delivered to client
     *  */
    @PatchMapping("/sell")
    @Transactional
    public ResponseEntity<?> sellStock(
            @RequestHeader (HttpHeaders.AUTHORIZATION) String bearerToken,
            @RequestBody SellStockRequest request) {
        try {
            stockMarketService.sellStock(bearerToken, request);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Transactional
    public ResponseEntity<?> getStock(@RequestBody SingleStockRequest request) {
        try {
            return ResponseEntity.ok(stockMarketService.getStock(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAllStocks() {
        return ResponseEntity.ok(stockMarketService.getAllStocks());
    }



    @GetMapping("/history")
    @Transactional
    public ResponseEntity<?> getDailyStockDataBetweenDates(
            @RequestParam String symbol,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to) {
        try {
            return ResponseEntity.ok(stockMarketService.getDailyStockDataBetweenDates(symbol, from, to));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
