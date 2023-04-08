package com.easytrade.server.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
@RequiredArgsConstructor
public class JsonWebTokenService {
    private static final String SECRET_KEY = "566D5970337336763979244226452948404D635166546A576E5A723474377721";
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken (UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken (Map<String, Object> extraClaims, UserDetails userDetails) {
        int tokenLifetimeMillis = 1000 * 60 * 15; // 15 minutes

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenLifetimeMillis))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * A user's token is valid if it matches the user and the token is not expired.
     * */
    public boolean isTokenValid(String tokenLiteral, UserDetails userDetails) {
        final String username = extractUsername(tokenLiteral);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(tokenLiteral);
    }

    public boolean isTokenExpired(String tokenLiteral) {
        return extractExpiration(tokenLiteral).before(new Date());
    }

    private Date extractExpiration (String tokenLiteral) {
        return extractClaim(tokenLiteral, Claims::getExpiration);
    }

    private Claims extractAllClaims(String tokenLiteral) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(tokenLiteral)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
