package com.flynn.crypttrade.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CryptoSimpleDto {
    private String id;
    private String name;
    private String image;
    private String URLtoCoinGecko;
}
