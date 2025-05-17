package com.flynn.crypttrade.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CryptoPriceHistoryDto {
    private Long id;
    private String symbol;
    private BigDecimal priceUsd;
    private LocalDateTime timestamp;
}
