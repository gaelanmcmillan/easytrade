package com.easytrade.server.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JsonWebTokenService {
    public class TokenAndExpiry {
        public String tokenLiteral;
        public Date expiration;
        public TokenAndExpiry(String token, Date date) {
            this.tokenLiteral = token;
            this.expiration = date;
        }
    }
    // TODO: Generate this secret key randomly ourselves... Not important right now!
    private static final String SECRET_KEY = "566D5970337336763979244226452948404D635166546A576E5A723474377721";
    String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public TokenAndExpiry generateToken (UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public TokenAndExpiry generateToken (Map<String, Object> extraClaims, UserDetails userDetails) {
        int tokenLifetimeMillis = 1000 * 60 * 15; // 15 minutes

        String rawToken = Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenLifetimeMillis))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        return new TokenAndExpiry(rawToken, extractExpiration(rawToken));
    }

    /**
     * A user's token is valid if it matches the user and the token is not expired.
     * */
    public boolean isTokenValid(String tokenLiteral, UserDetails userDetails) {
        final String username = extractUsername(tokenLiteral);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(tokenLiteral);
    }

    private boolean isTokenExpired(String tokenLiteral) {
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
