package com.flynn.crypttrade.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "crypto_price_history")
public class CryptoPriceHistory {
    @Id
    @GeneratedValue
    private Long id;

    private String symbol;
    private BigDecimal priceUsd;
    private LocalDateTime timestamp;
}
