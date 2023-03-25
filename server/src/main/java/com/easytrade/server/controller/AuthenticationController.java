package com.easytrade.server.controller;

import com.easytrade.server.dto.LoginRequest;
import com.easytrade.server.dto.SignupRequest;
import com.easytrade.server.exception.AccountWithUsernameExistsException;
import com.easytrade.server.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

/**
 * The <code>AuthenticationController</code> supplies API endpoints
 * for
 * */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/signup")
    public ResponseEntity<?> signup (@RequestBody SignupRequest request) {
        // ResponseEntity.ok() === HTTP 200
        try {
            return ResponseEntity.ok(authenticationService.signup(request));
        } catch (AccountWithUsernameExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(authenticationService.login(request));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
