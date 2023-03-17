package com.easytrade.server.service;

import com.easytrade.server.dto.LoginRequest;
import com.easytrade.server.dto.SignupRequest;
import com.easytrade.server.dto.AuthenticationResponse;
import com.easytrade.server.model.Token;
import com.easytrade.server.repository.TokenRepository;
import com.easytrade.server.model.TokenType;
import com.easytrade.server.model.Role;
import com.easytrade.server.model.User;
import com.easytrade.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JsonWebTokenService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse signup(SignupRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        var newUser = userRepository.save(user);
        var tokenLiteral = jwtService.generateToken(user);

        saveUserToken(newUser, tokenLiteral);

        return AuthenticationResponse.builder()
                .username(user.getUsername())
                .token(tokenLiteral)
                .build();
    }

    /**
     * Attempt to log the user in whose details are contained in <code>request</code>
     * */
    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        // TODO: Handle user doesn't exist?
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        var tokenLiteral = jwtService.generateToken(user);
        revokeAllUserTokens(user);

        saveUserToken(user, tokenLiteral);

        return AuthenticationResponse.builder()
                .username(user.getUsername())
                .token(tokenLiteral)
                .build();
    }

    private void saveUserToken(User user, String tokenLiteral) {
        var token = Token.builder()
                .user(user)
                .literal(tokenLiteral)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validTokens = tokenRepository.findAllValidByUserId(user.getId());
        if (validTokens.isEmpty()) return;

        validTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validTokens);
    }
}
