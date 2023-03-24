package com.easytrade.server.model;

import com.easytrade.server.exception.InsufficientFundsException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "easytrade_user")
public class User implements UserDetails, Purchaser {
    @Id
    @GeneratedValue
    private Integer id;

    private String firstName;
    private String lastName;
    private String username;
    private String password;

    private BigDecimal accountBalance;
    @Enumerated(EnumType.STRING)
    private Role role; // Represents the authorities a user has.

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void makePurchase(BigDecimal amount) throws InsufficientFundsException {
        if (getAccountBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds.");
        }
        setAccountBalance(getAccountBalance().subtract(amount));
    }
}
