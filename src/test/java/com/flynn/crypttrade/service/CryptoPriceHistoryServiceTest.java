package com.flynn.crypttrade.service;

import com.flynn.crypttrade.domain.CryptoPriceHistory;
import com.flynn.crypttrade.dto.CryptoPriceHistoryDto;
import com.flynn.crypttrade.mapper.CryptoPriceHistoryMapper;
import com.flynn.crypttrade.repository.CryptoPriceHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CryptoPriceHistoryServiceTest {

    @Mock
    private CryptoPriceHistoryRepository repository;

    @InjectMocks
    private CryptoPriceHistoryService service;

    private CryptoPriceHistoryDto dto;
    private CryptoPriceHistory entity;

    @BeforeEach
    void setUp() {
        dto = new CryptoPriceHistoryDto(null, "BTC", BigDecimal.valueOf(30000), LocalDateTime.now());
        entity = new CryptoPriceHistory();
        entity.setId(1L);
        entity.setSymbol("BTC");
        entity.setPriceUsd(BigDecimal.valueOf(30000));
        entity.setTimestamp(LocalDateTime.now());
    }

    @Test
    void shouldSavePriceHistory() {
        when(repository.save(any())).thenReturn(entity);

        CryptoPriceHistoryDto result = service.savePriceHistory(dto);

        assertEquals("BTC", result.getSymbol());
        assertEquals(BigDecimal.valueOf(30000), result.getPriceUsd());
    }

    @Test
    void shouldReturnAllPriceHistory() {
        when(repository.findAll()).thenReturn(List.of(entity));

        List<CryptoPriceHistoryDto> result = service.getAllPriceHistory();

        assertEquals(1, result.size());
        assertEquals("BTC", result.get(0).getSymbol());
    }

    @Test
    void shouldReturnHistoryForSymbol() {
        when(repository.findAllBySymbol("BTC")).thenReturn(List.of(entity));

        List<CryptoPriceHistoryDto> result = service.getPriceHistoryForSymbol("BTC");

        assertEquals(1, result.size());
        assertEquals("BTC", result.get(0).getSymbol());
    }

    @Test
    void shouldReturnLastPriceForSymbol() {
        when(repository.findTopBySymbolOrderByTimestampDesc("BTC")).thenReturn(Optional.of(entity));

        CryptoPriceHistoryDto result = service.getLastPriceForSymbol("BTC");

        assertEquals("BTC", result.getSymbol());
    }

    @Test
    void shouldThrowWhenNoLastPriceForSymbol() {
        when(repository.findTopBySymbolOrderByTimestampDesc("ETH")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.getLastPriceForSymbol("ETH"));
    }
}