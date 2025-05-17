package com.flynn.crypttrade.mapper;

import com.flynn.crypttrade.domain.CryptoBasic;
import com.flynn.crypttrade.dto.CryptoBasicDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CryptoBasicMapperTest {

    @Test
    void shouldMapEntityToDto() {
        CryptoBasic entity = new CryptoBasic();
        entity.setExternalId("bitcoin");
        entity.setSymbol("BTC");
        entity.setName("Bitcoin");
        entity.setImageUrl("http://image.url");
        entity.setCurrentPrice(BigDecimal.valueOf(30000));
        entity.setHigh24h(BigDecimal.valueOf(31000));
        entity.setLow24h(BigDecimal.valueOf(29000));
        entity.setPriceChange24h(BigDecimal.valueOf(-500));

        CryptoBasicDto dto = CryptoBasicMapper.toDto(entity);

        assertEquals("bitcoin", dto.getExternalId());
        assertEquals("BTC", dto.getSymbol());
        assertEquals("Bitcoin", dto.getName());
        assertEquals("http://image.url", dto.getImageUrl());
        assertEquals(BigDecimal.valueOf(30000), dto.getCurrentPrice());
        assertEquals(BigDecimal.valueOf(31000), dto.getHigh24h());
        assertEquals(BigDecimal.valueOf(29000), dto.getLow24h());
        assertEquals(BigDecimal.valueOf(-500), dto.getPriceChange24h());
    }

    @Test
    void shouldMapDtoToEntity() {
        CryptoBasicDto dto = new CryptoBasicDto();
        dto.setExternalId("ethereum");
        dto.setSymbol("ETH");
        dto.setName("Ethereum");
        dto.setImageUrl("http://eth.image");
        dto.setCurrentPrice(BigDecimal.valueOf(2000));
        dto.setHigh24h(BigDecimal.valueOf(2100));
        dto.setLow24h(BigDecimal.valueOf(1900));
        dto.setPriceChange24h(BigDecimal.valueOf(100));

        CryptoBasic entity = CryptoBasicMapper.toEntity(dto);

        assertEquals("ethereum", entity.getExternalId());
        assertEquals("ETH", entity.getSymbol());
        assertEquals("Ethereum", entity.getName());
        assertEquals("http://eth.image", entity.getImageUrl());
        assertEquals(BigDecimal.valueOf(2000), entity.getCurrentPrice());
        assertEquals(BigDecimal.valueOf(2100), entity.getHigh24h());
        assertEquals(BigDecimal.valueOf(1900), entity.getLow24h());
        assertEquals(BigDecimal.valueOf(100), entity.getPriceChange24h());
    }
}