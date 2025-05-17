package com.flynn.crypttrade.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "crypto_basic")
public class CryptoBasic {

    @Id
    private String externalId;


    private String symbol;
    private String name;
    private String imageUrl;

    private BigDecimal currentPrice;
    private BigDecimal high24h;
    private BigDecimal low24h;
    private BigDecimal priceChange24h;

}