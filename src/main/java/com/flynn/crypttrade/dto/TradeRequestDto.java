package com.flynn.crypttrade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeRequestDto {

    private Long userId;
    private String externalId;
    private BigDecimal amountUsd;
    private BigDecimal quantityCrypto;
}
