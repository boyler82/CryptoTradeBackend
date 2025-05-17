package com.flynn.crypttrade.mapper;

import com.flynn.crypttrade.domain.CryptoPriceHistory;
import com.flynn.crypttrade.dto.CryptoPriceHistoryDto;
import org.springframework.stereotype.Component;

@Component
public class CryptoPriceHistoryMapper {

    public static CryptoPriceHistory toEntity(CryptoPriceHistoryDto dto) {
        CryptoPriceHistory entity = new CryptoPriceHistory();
        entity.setId(dto.getId());
        entity.setSymbol(dto.getSymbol());
        entity.setPriceUsd(dto.getPriceUsd());
        entity.setTimestamp(dto.getTimestamp());
        return entity;
    }

    public static CryptoPriceHistoryDto toDto(CryptoPriceHistory entity) {
        return CryptoPriceHistoryDto.builder()
                .id(entity.getId())
                .symbol(entity.getSymbol())
                .priceUsd(entity.getPriceUsd())
                .timestamp(entity.getTimestamp())
                .build();
    }
}
