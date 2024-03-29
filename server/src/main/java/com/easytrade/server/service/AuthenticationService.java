package com.easytrade.server.service;

import com.easytrade.server.config.EasyTradeProperties;
import com.easytrade.server.dto.auth.LoginRequest;
import com.easytrade.server.dto.auth.SignupRequest;
import com.easytrade.server.dto.auth.AuthenticationResponse;
import com.easytrade.server.exception.AccountWithUsernameExistsException;
import com.easytrade.server.model.Role;
import com.easytrade.server.model.User;
import com.easytrade.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final EasyTradeProperties easyTradeProperties;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JsonWebTokenService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse signup(SignupRequest request) throws AccountWithUsernameExistsException {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .accountBalance(easyTradeProperties.getStartingBalance())
                .build();

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            String message = "An account with username '" + user.getUsername() + "' already exists.";
            throw new AccountWithUsernameExistsException(message);
        }

        userRepository.save(user);
        var tokenLiteral = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .username(user.getUsername())
                .token(tokenLiteral)
                .build();
    }

    /**
     * Attempt to log the user in whose details are contained in <code>request</code>
     * */
    @Transactional(readOnly = true)
    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        var tokenLiteral = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .username(user.getUsername())
                .token(tokenLiteral)
                .build();
    }
}
