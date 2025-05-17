package com.flynn.crypttrade.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletTransactionDto {
    private Long userId;
    private String externalId;
    private BigDecimal amountUsd;
    private BigDecimal amountCrypto;
}
