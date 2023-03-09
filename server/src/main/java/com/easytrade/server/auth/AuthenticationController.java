package com.easytrade.server.auth;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
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

//    @PostMapping("/logout")
//    public ResponseEntity<?> logout (@RequestBody LogoutRequest request) {
//        return ResponseEntity.ok(au)
//    }
}
