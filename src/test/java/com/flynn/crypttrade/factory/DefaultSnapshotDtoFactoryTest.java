package com.flynn.crypttrade.factory;


import com.flynn.crypttrade.client.CurrencyClient;
import com.flynn.crypttrade.domain.CryptoBasic;
import com.flynn.crypttrade.domain.User;
import com.flynn.crypttrade.domain.UserCryptoHolding;
import com.flynn.crypttrade.dto.PortfolioSnapshotDto;
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
class DefaultSnapshotDtoFactoryTest {

    @Mock
    private CurrencyClient currencyClient;

    @InjectMocks
    private DefaultSnapshotDtoFactory factory;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setWalletBalance(BigDecimal.valueOf(100));
    }

    @Test
    void shouldCreateSnapshotInUSD() {
        CryptoBasic crypto = new CryptoBasic();
        crypto.setCurrentPrice(BigDecimal.valueOf(200));

        UserCryptoHolding holding = new UserCryptoHolding();
        holding.setAmount(BigDecimal.valueOf(0.5));
        holding.setCryptoBasic(crypto);

        user.setHoldings(List.of(holding));

        PortfolioSnapshotDto snapshot = factory.createFromUser(user, "USD");

        assertEquals("USD", snapshot.getCurrency());
        assertEquals(BigDecimal.valueOf(100), snapshot.getFiatValue());
        assertEquals(BigDecimal.valueOf(100.0), snapshot.getCryptoValue());
        assertEquals(BigDecimal.valueOf(200.0), snapshot.getTotalValue());
    }

    @Test
    void shouldCreateSnapshotInOtherCurrency() {
        when(currencyClient.getExchangeRate("USD", "EUR")).thenReturn(BigDecimal.valueOf(2));

        user.setHoldings(List.of());

        PortfolioSnapshotDto snapshot = factory.createFromUser(user, "EUR");

        assertEquals("EUR", snapshot.getCurrency());
        assertEquals(BigDecimal.valueOf(200), snapshot.getTotalValue());
    }

    @Test
    void shouldThrowWhenCurrencyUnsupported() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                factory.createFromUser(user, "ABC")
        );

        assertTrue(ex.getMessage().contains("Unsupported currency"));
    }
}
