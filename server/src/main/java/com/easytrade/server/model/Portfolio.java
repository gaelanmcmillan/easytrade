package com.easytrade.server.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Portfolio {
    @Id
    @GeneratedValue
    private Integer id;

    //TODO: Do we want to allow fractional shares of stocks? (Changing Map's value type to double or something...)

    // TODO: Many to one could be appropriate if we want multiple portfolios per user.
    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

//    @ManyToMany
//    Map<Stock, Investment> investments;
}
