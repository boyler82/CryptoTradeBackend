package com.flynn.crypttrade.controller;


import com.flynn.crypttrade.client.CurrencyClient;
import com.flynn.crypttrade.domain.SupportedCurrency;
import com.flynn.crypttrade.domain.User;
import com.flynn.crypttrade.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/currency")
public class CurrencyController {

    private final CurrencyClient currencyClient;
    private final UserRepository userRepository;

    @GetMapping("/exchange")
    public ResponseEntity<BigDecimal> getRate(
            @RequestParam String from,
            @RequestParam String to
    ) {
        return ResponseEntity.ok(currencyClient.getExchangeRate(from.toUpperCase(), to.toUpperCase()));
    }

    @GetMapping("/currencies")
    public ResponseEntity<List<String>> getSupportedCurrencies() {
        return ResponseEntity.ok(
                Arrays.stream(SupportedCurrency.values())
                        .map(Enum::name)
                        .toList()
        );
    }

    @GetMapping("/user/{userId}/balance-in/{currency}")
    public ResponseEntity<BigDecimal> getBalanceInCurrency(
            @PathVariable Long userId,
            @PathVariable String currency
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        BigDecimal balanceUSD = user.getWalletBalance();
        BigDecimal exchangeRate = currencyClient.getExchangeRate("USD", currency.toUpperCase());

        BigDecimal converted = balanceUSD.multiply(exchangeRate).setScale(2, RoundingMode.HALF_UP);
        return ResponseEntity.ok(converted);
    }
}