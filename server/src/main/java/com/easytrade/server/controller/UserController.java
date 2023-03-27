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
    @GetMapping("/portfolio/{username}")
    public ResponseEntity<?> getUserPortfolio(@PathVariable String username) {
        try {
            return ResponseEntity.ok().body(portfolioService.getUserPortfolio(username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
