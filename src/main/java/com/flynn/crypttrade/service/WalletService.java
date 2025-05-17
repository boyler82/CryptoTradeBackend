package com.flynn.crypttrade.service;

import com.flynn.crypttrade.domain.CryptoBasic;
import com.flynn.crypttrade.domain.User;
import com.flynn.crypttrade.domain.UserCryptoHolding;
import com.flynn.crypttrade.dto.WalletTransactionDto;
import com.flynn.crypttrade.repository.CryptoBasicRepository;
import com.flynn.crypttrade.repository.UserCryptoHoldingRepository;
import com.flynn.crypttrade.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final UserRepository userRepository;
    private final CryptoBasicRepository cryptoBasicRepository;
    private final UserCryptoHoldingRepository holdingRepository;
    private final UserCryptoHoldingService userCryptoHoldingService;

    public void buyCrypto(WalletTransactionDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        CryptoBasic crypto = cryptoBasicRepository.findByExternalId(dto.getExternalId())
                .orElseThrow(() -> new RuntimeException("Crypto not found"));

        if (user.getWalletBalance().compareTo(dto.getAmountUsd()) < 0) {
            throw new RuntimeException("Not enough balance!");
        }

        BigDecimal cryptoAmount = dto.getAmountUsd()
                .divide(crypto.getCurrentPrice(), 8, RoundingMode.HALF_UP);

        UserCryptoHolding holding = userCryptoHoldingService.getOrCreateHolding(user, crypto);

        holding.setAmount(
                Optional.ofNullable(holding.getAmount()).orElse(BigDecimal.ZERO)
                        .add(cryptoAmount)
        );

        user.setWalletBalance(user.getWalletBalance().subtract(dto.getAmountUsd()));

        userCryptoHoldingService.saveHolding(holding);
        userRepository.save(user);
    }

    public void sellCrypto(WalletTransactionDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        CryptoBasic crypto = cryptoBasicRepository.findByExternalId(dto.getExternalId())
                .orElseThrow(() -> new RuntimeException("Crypto not found"));

        UserCryptoHolding holding = holdingRepository.findByUserAndCryptoBasic(user, crypto)
                .orElseThrow(() -> new RuntimeException("Holding not found"));

        BigDecimal amountToSell = dto.getAmountCrypto();

        if (amountToSell == null) {
            amountToSell = holding.getAmount();
        }

        if (holding.getAmount().compareTo(amountToSell) < 0) {
            throw new RuntimeException("Not enough crypto to sell!");
        }

        BigDecimal usdGained = amountToSell
                .multiply(crypto.getCurrentPrice())
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal updatedAmount = holding.getAmount().subtract(amountToSell);
        holding.setAmount(updatedAmount);
        user.setWalletBalance(user.getWalletBalance().add(usdGained));

        if (updatedAmount.compareTo(BigDecimal.ZERO) == 0) {
            holdingRepository.delete(holding);
        } else {
            holdingRepository.save(holding);
        }

        userRepository.save(user);
    }

}

