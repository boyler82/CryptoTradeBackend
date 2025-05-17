package com.flynn.crypttrade.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CurrencyClient {

    private final RestTemplate restTemplate;

    @Value("${currency.api.url}")
    private String currencyApiUrl;

    @Value("${currency.api.key}")
    private String apiKey;

    public BigDecimal getExchangeRate(String baseCurrency, String targetCurrency) {
        if (baseCurrency.equalsIgnoreCase(targetCurrency)) {
            return BigDecimal.ONE;
        }

        URI uri = UriComponentsBuilder.fromHttpUrl(currencyApiUrl + "/latest")
                .queryParam("apikey", apiKey)
                .queryParam("base_currency", baseCurrency)
                .queryParam("currencies", targetCurrency)
                .build()
                .toUri();

        Map<String, Object> response = restTemplate.getForObject(uri, Map.class);
        if (response == null || !response.containsKey("data")) {
            throw new RuntimeException("No exchange data available");
        }

        Map<String, Double> data = (Map<String, Double>) response.get("data");

        Double rate = data.get(targetCurrency.toUpperCase());
        if (rate == null) {
            throw new RuntimeException("Exchange rate not found for " + targetCurrency);
        }

        return BigDecimal.valueOf(rate);
    }
}
