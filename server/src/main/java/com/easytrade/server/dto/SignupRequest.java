package com.easytrade.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A <code>SignupRequest</code> is a wrapper for all the information
 * we would like to require from a user when signing up for our service.
 * */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
