package com.flynn.crypttrade.factory;

import com.flynn.crypttrade.client.CurrencyClient;
import com.flynn.crypttrade.domain.PortfolioSnapshot;
import com.flynn.crypttrade.domain.SupportedCurrency;
import com.flynn.crypttrade.domain.User;
import com.flynn.crypttrade.dto.PortfolioSnapshotDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DefaultSnapshotDtoFactory implements SnapshotDtoFactory {

    private final CurrencyClient currencyClient;

    @Override
    public PortfolioSnapshotDto createFromUser(User user, String currency) {
        if (!SupportedCurrency.isSupported(currency)) {
            throw new IllegalArgumentException("Unsupported currency: " + currency);
        }
        BigDecimal fiat = user.getWalletBalance() != null ? user.getWalletBalance() : BigDecimal.ZERO;

        BigDecimal cryptoValue = user.getHoldings() != null
                ? user.getHoldings().stream()
                .map(h -> {
                    BigDecimal price = h.getCryptoBasic().getCurrentPrice() != null
                            ? h.getCryptoBasic().getCurrentPrice()
                            : BigDecimal.ZERO;
                    BigDecimal amount = h.getAmount() != null ? h.getAmount() : BigDecimal.ZERO;
                    return price.multiply(amount);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                : BigDecimal.ZERO;

        BigDecimal total = fiat.add(cryptoValue);

        if (!"USD".equalsIgnoreCase(currency)) {
            BigDecimal rate = currencyClient.getExchangeRate("USD", currency);
            fiat = fiat.multiply(rate);
            cryptoValue = cryptoValue.multiply(rate);
            total = total.multiply(rate);
        }

        return PortfolioSnapshotDto.builder()
                .fiatValue(fiat)
                .cryptoValue(cryptoValue)
                .totalValue(total)
                .currency(currency.toUpperCase())
                .snapshotDate(LocalDate.now())
                .build();
    }


}