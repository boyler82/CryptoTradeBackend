package com.flynn.crypttrade.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioSnapshotDto {
    private Long id;
    private BigDecimal fiatValue;
    private BigDecimal cryptoValue;
    private BigDecimal totalValue;
    private String currency;
    private LocalDate snapshotDate;
}
