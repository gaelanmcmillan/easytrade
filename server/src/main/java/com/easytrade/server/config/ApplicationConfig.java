package com.easytrade.server.config;

import com.easytrade.server.repository.TokenRepository;
import com.easytrade.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.io.IOException;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(EasyTradeProperties.class)
public class ApplicationConfig {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found."));

    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LogoutHandler logoutHandler() {
        return (request, response, authentication) -> {
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
        };
    }
}
