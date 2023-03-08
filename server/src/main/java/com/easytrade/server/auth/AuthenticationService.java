package com.easytrade.server.auth;

import com.easytrade.server.config.JwtService;
import com.easytrade.server.token.Token;
import com.easytrade.server.token.TokenRepository;
import com.easytrade.server.token.TokenType;
import com.easytrade.server.user.Role;
import com.easytrade.server.user.User;
import com.easytrade.server.user.UserRepository;
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
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse signup(SignupRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        var newUser = userRepository.save(user);
        var rawToken = jwtService.generateToken(user);

        saveUserToken(newUser, rawToken);

        return AuthenticationResponse.builder()
                .token(rawToken)
                .build();
    }

    /**
     * Attempt to log the user in whose details are contained in <code>request</code>
     * */
    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        // TODO: Handle user doesn't exist?
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var rawToken = jwtService.generateToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, rawToken);

        return AuthenticationResponse.builder()
                .token(rawToken)
                .build();
    }

    private void saveUserToken(User user, String rawToken) {
        var token = Token.builder()
                .user(user)
                .token(rawToken)
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
