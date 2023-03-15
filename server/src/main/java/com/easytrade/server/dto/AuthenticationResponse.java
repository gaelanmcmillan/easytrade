package com.easytrade.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * An <code>AuthenticationResponse</code> provides a uniform wrapper for any object
 * we would like to send to a client on a successful authentication action (e.g. signup, login).
 * <br><br>
 * Currently just wraps a String `token`, which is the token JSON Web Token the client
 * can cache in order to make authenticated requests to the server.
 * */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String username;
    private String token;
}
