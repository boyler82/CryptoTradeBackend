package com.flynn.crypttrade.service;

import com.flynn.crypttrade.domain.CryptoBasic;
import com.flynn.crypttrade.domain.User;
import com.flynn.crypttrade.domain.UserCryptoHolding;
import com.flynn.crypttrade.dto.WalletTransactionDto;
import com.flynn.crypttrade.repository.CryptoBasicRepository;
import com.flynn.crypttrade.repository.UserCryptoHoldingRepository;
import com.flynn.crypttrade.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @InjectMocks
    private WalletService walletService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CryptoBasicRepository cryptoBasicRepository;

    @Mock
    private UserCryptoHoldingRepository holdingRepository;

    @Mock
    private UserCryptoHoldingService userCryptoHoldingService;

    private User user;
    private CryptoBasic crypto;
    private WalletTransactionDto dto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setWalletBalance(BigDecimal.valueOf(100));

        crypto = new CryptoBasic();
        crypto.setExternalId("bitcoin");
        crypto.setCurrentPrice(BigDecimal.valueOf(50000));

        dto = new WalletTransactionDto();
        dto.setUserId(1L);
        dto.setExternalId("bitcoin");
        dto.setAmountUsd(BigDecimal.valueOf(50));
    }

    @Test
    void shouldBuyCryptoSuccessfully() {
        UserCryptoHolding holding = new UserCryptoHolding();
        holding.setAmount(BigDecimal.valueOf(0));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cryptoBasicRepository.findByExternalId("bitcoin")).thenReturn(Optional.of(crypto));
        when(userCryptoHoldingService.getOrCreateHolding(user, crypto)).thenReturn(holding);

        walletService.buyCrypto(dto);

        verify(userRepository).save(user);
        verify(userCryptoHoldingService).saveHolding(holding);
        assertEquals(BigDecimal.valueOf(100).subtract(BigDecimal.valueOf(50)), user.getWalletBalance());
    }

    @Test
    void shouldThrowWhenNotEnoughBalance() {
        user.setWalletBalance(BigDecimal.valueOf(10));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cryptoBasicRepository.findByExternalId("bitcoin")).thenReturn(Optional.of(crypto));

        assertThrows(RuntimeException.class, () -> walletService.buyCrypto(dto));
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> walletService.buyCrypto(dto));
    }

    @Test
    void shouldThrowWhenCryptoNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cryptoBasicRepository.findByExternalId("bitcoin")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> walletService.buyCrypto(dto));
    }

    @Test
    void shouldSellCryptoSuccessfully() {
        UserCryptoHolding holding = new UserCryptoHolding();
        holding.setAmount(BigDecimal.valueOf(1));

        dto.setAmountCrypto(BigDecimal.valueOf(0.5));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cryptoBasicRepository.findByExternalId("bitcoin")).thenReturn(Optional.of(crypto));
        when(holdingRepository.findByUserAndCryptoBasic(user, crypto)).thenReturn(Optional.of(holding));

        walletService.sellCrypto(dto);

        verify(userRepository).save(user);
        verify(holdingRepository).save(holding);
        assertEquals(BigDecimal.valueOf(0.5), holding.getAmount());
    }

    @Test
    void shouldDeleteHoldingWhenAmountZero() {
        UserCryptoHolding holding = new UserCryptoHolding();
        holding.setAmount(BigDecimal.valueOf(1));

        dto.setAmountCrypto(BigDecimal.valueOf(1));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cryptoBasicRepository.findByExternalId("bitcoin")).thenReturn(Optional.of(crypto));
        when(holdingRepository.findByUserAndCryptoBasic(user, crypto)).thenReturn(Optional.of(holding));

        walletService.sellCrypto(dto);

        verify(holdingRepository).delete(holding);
    }

    @Test
    void shouldThrowWhenSellingMoreThanHolding() {
        UserCryptoHolding holding = new UserCryptoHolding();
        holding.setAmount(BigDecimal.valueOf(0.5));

        dto.setAmountCrypto(BigDecimal.valueOf(1));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cryptoBasicRepository.findByExternalId("bitcoin")).thenReturn(Optional.of(crypto));
        when(holdingRepository.findByUserAndCryptoBasic(user, crypto)).thenReturn(Optional.of(holding));

        assertThrows(RuntimeException.class, () -> walletService.sellCrypto(dto));
    }

    @Test
    void shouldThrowWhenSellingAndUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> walletService.sellCrypto(dto));
    }

    @Test
    void shouldThrowWhenSellingAndCryptoNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cryptoBasicRepository.findByExternalId("bitcoin")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> walletService.sellCrypto(dto));
    }

    @Test
    void shouldThrowWhenHoldingNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cryptoBasicRepository.findByExternalId("bitcoin")).thenReturn(Optional.of(crypto));
        when(holdingRepository.findByUserAndCryptoBasic(user, crypto)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> walletService.sellCrypto(dto));
    }
}
