package com.flynn.crypttrade.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "portfolio_snapshots")
public class PortfolioSnapshot {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal fiatValue;
    private BigDecimal cryptoValue;
    private BigDecimal totalValue;
    private String currency;
    private LocalDate snapshotDate;
}
