package com.easytrade.server.token;

import com.easytrade.server.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    @Id
    @GeneratedValue
    public Integer id;

    @Column(unique = true)
    public String literal;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    public boolean revoked;
    public boolean expired;

    public Date expiration;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;
}
