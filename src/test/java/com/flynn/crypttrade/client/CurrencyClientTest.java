package com.flynn.crypttrade.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CurrencyClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CurrencyClient currencyClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(currencyClient, "currencyApiUrl", "https://api.example.com");
        ReflectionTestUtils.setField(currencyClient, "apiKey", "test_api_key");
    }

    @Test
    void shouldReturnOneWhenCurrenciesAreEqual() {
        BigDecimal rate = currencyClient.getExchangeRate("USD", "USD");
        assertEquals(BigDecimal.ONE, rate);
    }

    @Test
    void shouldReturnExchangeRate() {
        Map<String, Double> data = new HashMap<>();
        data.put("EUR", 0.85);
        Map<String, Object> response = new HashMap<>();
        response.put("data", data);

        when(restTemplate.getForObject(any(URI.class), eq(Map.class))).thenReturn(response);

        BigDecimal rate = currencyClient.getExchangeRate("USD", "EUR");

        assertEquals(BigDecimal.valueOf(0.85), rate);
    }

    @Test
    void shouldThrowWhenNoData() {
        when(restTemplate.getForObject(any(URI.class), eq(Map.class))).thenReturn(null);
        assertThrows(RuntimeException.class, () -> currencyClient.getExchangeRate("USD", "EUR"));
    }

    @Test
    void shouldThrowWhenTargetCurrencyNotFound() {
        Map<String, Double> data = new HashMap<>();
        Map<String, Object> response = new HashMap<>();
        response.put("data", data);

        when(restTemplate.getForObject(any(URI.class), eq(Map.class))).thenReturn(response);

        assertThrows(RuntimeException.class, () -> currencyClient.getExchangeRate("USD", "EUR"));
    }
}