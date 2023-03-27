package com.easytrade.server.service;

import com.easytrade.server.dto.InvestmentDTO;
import com.easytrade.server.dto.PortfolioRequest;
import com.easytrade.server.dto.PortfolioResponse;
import com.easytrade.server.exception.NonexistentUserException;
import com.easytrade.server.model.UserStockHolding;
import com.easytrade.server.repository.UserRepository;
import com.easytrade.server.repository.UserStockHoldingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final UserStockHoldingRepository userStockHoldingRepository;
    private final UserRepository userRepository;

    public PortfolioResponse getUserPortfolio(String username) throws NonexistentUserException {
        // Error case: User doesn't exist
        if (userRepository.findByUsername(username).isEmpty()) {
            throw new NonexistentUserException(username);
        }

        List<UserStockHolding> investments = userStockHoldingRepository.getUserStockHoldingsByUsername(username);
        List<InvestmentDTO> viewableInvestments = investments.stream()
                .map(inv -> InvestmentDTO.builder()
                                .stock(inv.getStock())
                                .quantity(inv.getQuantity())
                                .build())
                .toList();

        return PortfolioResponse.builder()
                .username(username)
                .investments(viewableInvestments)
                .build();
    }
}
