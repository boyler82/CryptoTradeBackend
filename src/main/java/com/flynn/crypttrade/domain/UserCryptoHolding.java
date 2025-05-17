package com.flynn.crypttrade.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_crypto_holdings")
public class UserCryptoHolding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "crypto_basic_id")
    private CryptoBasic cryptoBasic;

    @Column(precision = 30, scale = 10)
    private BigDecimal amount;
    private String currency;
    private LocalDateTime createdAt;
}