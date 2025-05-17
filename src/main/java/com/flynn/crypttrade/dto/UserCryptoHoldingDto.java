package com.flynn.crypttrade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCryptoHoldingDto {

    private Long id;
    private Long userId;
    private String cryptoBasicId;
    private BigDecimal amount;
    private String currency;
    private LocalDateTime createdAt;
}
