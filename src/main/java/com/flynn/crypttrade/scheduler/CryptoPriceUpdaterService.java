package com.flynn.crypttrade.scheduler;

import com.flynn.crypttrade.client.CoinGeckoClient;
import com.flynn.crypttrade.domain.CryptoBasic;
import com.flynn.crypttrade.domain.UserCryptoHolding;
import com.flynn.crypttrade.dto.CoinGeckoCryptoDto;
import com.flynn.crypttrade.dto.CryptoPriceHistoryDto;
import com.flynn.crypttrade.repository.CryptoBasicRepository;
import com.flynn.crypttrade.repository.UserCryptoHoldingRepository;
import com.flynn.crypttrade.service.CryptoPriceHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class CryptoPriceUpdaterService {

    private final CryptoBasicRepository cryptoBasicRepository;
    private final UserCryptoHoldingRepository userCryptoHoldingRepository;
    private final CoinGeckoClient coinGeckoClient;
    private final CryptoPriceHistoryService cryptoPriceHistoryService;

    @Scheduled(cron = "0 */5 * * * *")
    public void updateCryptoHoldingsValue() {
        log.info("Start updating user crypto holdings values...");

        List<UserCryptoHolding> holdings = userCryptoHoldingRepository.findAll();

        for (UserCryptoHolding holding : holdings) {
            CryptoBasic crypto = holding.getCryptoBasic();
            if (crypto != null) {
                try {
                    CoinGeckoCryptoDto updated = coinGeckoClient.fetchCryptoBasic(crypto.getExternalId());

                    crypto.setCurrentPrice(updated.getCurrentPrice());
                    crypto.setHigh24h(updated.getHigh24h());
                    crypto.setLow24h(updated.getLow24h());
                    crypto.setPriceChange24h(updated.getPriceChange24h());

                    cryptoBasicRepository.save(crypto);

                    CryptoPriceHistoryDto historyDto = CryptoPriceHistoryDto.builder()
                            .symbol(crypto.getSymbol())
                            .priceUsd(crypto.getCurrentPrice())
                            .timestamp(LocalDateTime.now())
                            .build();

                    cryptoPriceHistoryService.savePriceHistory(historyDto);

                } catch (Exception e) {
                    log.error("Error updating price for crypto: " + crypto.getName(), e);
                }
            }
        }

        log.info("Finished updating and saving crypto price history.");
    }
}