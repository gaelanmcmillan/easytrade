package com.easytrade.server.config;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class RequestParser {
    public static Optional<String> extractBearerToken(HttpServletRequest request) {
        final String BEARER_PREFIX = "Bearer ";
        final String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith(BEARER_PREFIX)) {
            return Optional.empty();
        }

        return Optional.of(header.substring(BEARER_PREFIX.length()));
    }
}
