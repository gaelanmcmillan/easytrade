package com.easytrade.server.controller;

import com.easytrade.server.dto.LoginRequest;
import com.easytrade.server.dto.SignupRequest;
import com.easytrade.server.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }
}
