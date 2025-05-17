package com.flynn.crypttrade.mapper;

import com.flynn.crypttrade.domain.CryptoBasic;
import com.flynn.crypttrade.dto.CryptoBasicDto;
import org.springframework.stereotype.Component;

@Component
public class CryptoBasicMapper {

    public static CryptoBasicDto toDto(CryptoBasic entity) {
        CryptoBasicDto dto = new CryptoBasicDto();
        dto.setExternalId(entity.getExternalId());
        dto.setSymbol(entity.getSymbol());
        dto.setName(entity.getName());
        dto.setImageUrl(entity.getImageUrl());
        dto.setCurrentPrice(entity.getCurrentPrice());
        dto.setHigh24h(entity.getHigh24h());
        dto.setLow24h(entity.getLow24h());
        dto.setPriceChange24h(entity.getPriceChange24h());
        return dto;
    }

    public static CryptoBasic toEntity(CryptoBasicDto dto) {
        CryptoBasic entity = new CryptoBasic();
        entity.setExternalId(dto.getExternalId());
        entity.setSymbol(dto.getSymbol());
        entity.setName(dto.getName());
        entity.setImageUrl(dto.getImageUrl());
        entity.setCurrentPrice(dto.getCurrentPrice());
        entity.setHigh24h(dto.getHigh24h());
        entity.setLow24h(dto.getLow24h());
        entity.setPriceChange24h(dto.getPriceChange24h());
        return entity;
    }
}
