package com.flynn.crypttrade.client;

import com.flynn.crypttrade.config.CoinGeckoConfig;
import com.flynn.crypttrade.dto.CoinGeckoCryptoDto;
import com.flynn.crypttrade.dto.CryptoBasicDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CoinGeckoClient {

    private final RestTemplate restTemplate;
    private final CoinGeckoConfig coinGeckoConfig;

    public CoinGeckoCryptoDto fetchCryptoBasic(String id) {
        URI url = UriComponentsBuilder
                .fromHttpUrl(coinGeckoConfig.getApiUrl() + "/coins/markets")
                .queryParam("vs_currency", "usd")
                .queryParam("ids", id)
                .queryParam("order", "market_cap_desc")
                .queryParam("per_page", 1)
                .queryParam("page", 1)
                .queryParam("sparkline", false)
                .build()
                .encode()
                .toUri();

        CoinGeckoCryptoDto[] cryptos = restTemplate.getForObject(url, CoinGeckoCryptoDto[].class);

        if (cryptos != null && cryptos.length > 0) {
            return cryptos[0];
        } else {
            throw new RuntimeException("Crypto not found: " + id);
        }
    }

    public List<CoinGeckoCryptoDto> fetchAllCryptosBasic() {
        URI url = UriComponentsBuilder.fromHttpUrl(coinGeckoConfig.getApiUrl() + "/coins/markets")
                .queryParam("vs_currency", "usd")
                .queryParam("order", "market_cap_desc")
                .queryParam("per_page", 10)
                .queryParam("page", 1)
                .queryParam("sparkline", false)
                .build()
                .encode()
                .toUri();

        CoinGeckoCryptoDto[] cryptos = restTemplate.getForObject(url, CoinGeckoCryptoDto[].class);

        return Arrays.asList(cryptos);
    }
}
