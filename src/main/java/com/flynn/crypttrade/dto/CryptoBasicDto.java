package com.flynn.crypttrade.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CryptoBasicDto {

    private String externalId;
    private String symbol;
    private String name;
    private String imageUrl;
    private BigDecimal currentPrice;
    private BigDecimal high24h;

    private BigDecimal low24h;

    private BigDecimal priceChange24h;

}
