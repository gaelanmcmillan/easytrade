package com.easytrade.server.config;

import com.easytrade.server.service.JsonWebTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JsonWebTokenAuthenticationFilter extends OncePerRequestFilter {
    private final JsonWebTokenService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final Optional<String> maybeToken = extractBearerToken(request);
        // We can return early if no token was extracted.
        if (maybeToken.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }
        final String tokenLiteral = maybeToken.get();
        final String username = jwtService.extractUsername(tokenLiteral);

        // If the username is invalid or the user is already authenticated, we can return early.
        if (username == null || SecurityContextHolder.getContext().getAuthentication() != null) {
           filterChain.doFilter(request, response);
           return;
        }

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

        if (jwtService.isTokenValid(tokenLiteral, userDetails)) {
            var authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }

    private Optional<String> extractBearerToken(HttpServletRequest request) {
        final String BEARER_PREFIX = "Bearer ";
        final String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith(BEARER_PREFIX)) {
            return Optional.empty();
        }

        return Optional.of(header.substring(BEARER_PREFIX.length()));
    }
}
