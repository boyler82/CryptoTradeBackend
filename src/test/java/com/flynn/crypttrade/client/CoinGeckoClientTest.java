package com.flynn.crypttrade.client;


import com.flynn.crypttrade.config.CoinGeckoConfig;
import com.flynn.crypttrade.dto.CoinGeckoCryptoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoinGeckoClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CoinGeckoConfig coinGeckoConfig;

    @InjectMocks
    private CoinGeckoClient coinGeckoClient;

    @BeforeEach
    void setup() {
        when(coinGeckoConfig.getApiUrl()).thenReturn("https://api.coingecko.com/api/v3");
    }

    @Test
    void shouldFetchCryptoBasic() {
        // Given
        CoinGeckoCryptoDto mockCrypto = new CoinGeckoCryptoDto();
        mockCrypto.setExternalId("bitcoin");
        mockCrypto.setSymbol("btc");
        mockCrypto.setCurrentPrice(BigDecimal.valueOf(30000));

        CoinGeckoCryptoDto[] response = new CoinGeckoCryptoDto[]{mockCrypto};
        when(restTemplate.getForObject(any(URI.class), eq(CoinGeckoCryptoDto[].class))).thenReturn(response);

        // When
        CoinGeckoCryptoDto result = coinGeckoClient.fetchCryptoBasic("bitcoin");

        // Then
        assertNotNull(result);
        assertEquals("bitcoin", result.getExternalId());
        assertEquals("btc", result.getSymbol());
    }

    @Test
    void shouldThrowWhenNoCryptoFound() {
        when(restTemplate.getForObject(any(URI.class), eq(CoinGeckoCryptoDto[].class)))
                .thenReturn(new CoinGeckoCryptoDto[]{});

        assertThrows(RuntimeException.class, () -> coinGeckoClient.fetchCryptoBasic("nonexistent"));
    }

    @Test
    void shouldFetchAllCryptosBasic() {
        CoinGeckoCryptoDto crypto1 = new CoinGeckoCryptoDto();
        crypto1.setExternalId("bitcoin");
        CoinGeckoCryptoDto crypto2 = new CoinGeckoCryptoDto();
        crypto2.setExternalId("ethereum");

        CoinGeckoCryptoDto[] response = new CoinGeckoCryptoDto[]{crypto1, crypto2};
        when(restTemplate.getForObject(any(URI.class), eq(CoinGeckoCryptoDto[].class))).thenReturn(response);

        List<CoinGeckoCryptoDto> result = coinGeckoClient.fetchAllCryptosBasic();

        assertEquals(2, result.size());
        assertEquals("bitcoin", result.get(0).getExternalId());
    }
}