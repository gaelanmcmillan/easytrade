package com.easytrade.server.service;

import com.easytrade.server.dto.user.InvestmentDTO;
import com.easytrade.server.dto.user.PortfolioResponse;
import com.easytrade.server.exception.NonexistentUserException;
import com.easytrade.server.model.Investment;
import com.easytrade.server.repository.UserRepository;
import com.easytrade.server.repository.InvestmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final InvestmentRepository investmentRepository;
    private final UserRepository userRepository;

    public PortfolioResponse getUserPortfolio(String username) throws NonexistentUserException {
        // Error case: User doesn't exist
        if (userRepository.findByUsername(username).isEmpty()) {
            throw new NonexistentUserException(username);
        }

        List<Investment> investments = investmentRepository.getUserStockHoldingsByUsername(username);
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
