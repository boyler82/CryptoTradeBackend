package com.flynn.crypttrade.service;

import com.flynn.crypttrade.client.CoinGeckoClient;
import com.flynn.crypttrade.domain.CryptoBasic;
import com.flynn.crypttrade.dto.CoinGeckoCryptoDto;
import com.flynn.crypttrade.repository.CryptoBasicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CryptoBasicServiceTest {

    @InjectMocks
    private CryptoBasicService cryptoBasicService;

    @Mock
    private CoinGeckoClient coinGeckoClient;

    @Mock
    private CryptoBasicRepository cryptoBasicRepository;

    private CoinGeckoCryptoDto dto;

    @BeforeEach
    void setUp() {
        dto = new CoinGeckoCryptoDto();
        dto.setExternalId("btc");
        dto.setSymbol("BTC");
        dto.setName("Bitcoin");
        dto.setImageUrl("http://image.url");
        dto.setCurrentPrice(BigDecimal.valueOf(30000));
        dto.setHigh24h(BigDecimal.valueOf(31000));
        dto.setLow24h(BigDecimal.valueOf(29000));
        dto.setPriceChange24h(BigDecimal.valueOf(-500));
    }

    @Test
    void shouldSaveCrypto() {
        when(coinGeckoClient.fetchCryptoBasic("btc")).thenReturn(dto);
        when(cryptoBasicRepository.save(any(CryptoBasic.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CryptoBasic saved = cryptoBasicService.saveCrypto("btc");

        assertNotNull(saved);
        assertEquals("btc", saved.getExternalId());
        assertEquals("Bitcoin", saved.getName());
        verify(cryptoBasicRepository).save(any(CryptoBasic.class));
    }

    @Test
    void shouldFetchAndSaveAllCryptos() {
        when(coinGeckoClient.fetchAllCryptosBasic()).thenReturn(List.of(dto));

        cryptoBasicService.fetchAllCryptos();

        verify(cryptoBasicRepository, times(1)).save(any(CryptoBasic.class));
    }

}