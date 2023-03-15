package com.easytrade.server.config;

import com.easytrade.server.token.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final TokenRepository tokenRepository;


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final Optional<String> maybeToken = RequestParser.extractBearerToken(request);
        // We can return early if no token was extracted.
        if (maybeToken.isEmpty()) {
            return;
        }

        final String tokenLiteral = maybeToken.get();

        tokenRepository.findByLiteral(tokenLiteral).ifPresent(token -> {
            token.setExpired(true);
            token.setRevoked(true);
            tokenRepository.save(token);
            SecurityContextHolder.clearContext();
        });
    }
}
