package com.easytrade.server.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Currently just wraps a String `token`, which is the token JSON Web Token the client
 * can cache in order to make authenticated requests to the server.
 * */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
}
