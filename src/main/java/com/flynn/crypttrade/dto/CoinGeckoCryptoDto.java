package com.flynn.crypttrade.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoinGeckoCryptoDto {

    @JsonProperty("id")
    private String externalId;

    private String symbol;
    private String name;

    @JsonProperty("image")
    private String imageUrl;

    @JsonProperty("current_price")
    private BigDecimal currentPrice;

    @JsonProperty("high_24h")
    private BigDecimal high24h;

    @JsonProperty("low_24h")
    private BigDecimal low24h;

    @JsonProperty("price_change_24h")
    private BigDecimal priceChange24h;
}
