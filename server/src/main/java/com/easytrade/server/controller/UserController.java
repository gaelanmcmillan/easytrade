package com.easytrade.server.controller;

import com.easytrade.server.dto.PortfolioRequest;
import com.easytrade.server.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final PortfolioService portfolioService;
    @GetMapping("/portfolio")
    public ResponseEntity<?> getUserPortfolio(@RequestBody PortfolioRequest request) {
        try {
            return ResponseEntity.ok().body(portfolioService.getUserPortfolio(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
