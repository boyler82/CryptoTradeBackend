package com.flynn.crypttrade.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private String passwordHash;
    private LocalDateTime createdAt;

    @Column(name = "wallet_balance")
    private BigDecimal walletBalance;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<UserCryptoHolding> holdings;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<LoginHistory> loginHistory;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private NotificationSettings notificationSettings;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PortfolioSnapshot> snapshots;
}
