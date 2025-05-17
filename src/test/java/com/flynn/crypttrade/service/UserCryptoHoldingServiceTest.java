package com.flynn.crypttrade.service;

import static org.junit.jupiter.api.Assertions.*;

import com.flynn.crypttrade.domain.CryptoBasic;
import com.flynn.crypttrade.domain.User;
import com.flynn.crypttrade.domain.UserCryptoHolding;
import com.flynn.crypttrade.dto.UserCryptoHoldingDto;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserCryptoHoldingServiceTest {

    @InjectMocks
    private UserCryptoHoldingService holdingService;

    @Mock
    private UserCryptoHoldingRepository holdingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CryptoBasicRepository cryptoBasicRepository;

    private User user;
    private CryptoBasic crypto;
    private UserCryptoHoldingDto dto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        crypto = new CryptoBasic();
        crypto.setExternalId("bitcoin");

        dto = new UserCryptoHoldingDto();
        dto.setUserId(1L);
        dto.setCryptoBasicId("bitcoin");
        dto.setAmount(BigDecimal.valueOf(0.01));
        dto.setCurrency("USD");
    }

    @Test
    void shouldSaveHolding() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cryptoBasicRepository.findByExternalId("bitcoin")).thenReturn(Optional.of(crypto));
        when(holdingRepository.save(any(UserCryptoHolding.class)))
                .thenAnswer(invocation -> invocation.getArgument(0)); // ← to dodaj

        UserCryptoHolding saved = holdingService.saveHolding(dto);

        assertNotNull(saved);
        assertEquals("USD", saved.getCurrency());
        verify(holdingRepository).save(any(UserCryptoHolding.class));
    }

    @Test
    void shouldGetOrCreateNewHoldingIfNotExists() {
        when(holdingRepository.findByUserAndCryptoBasic(user, crypto)).thenReturn(Optional.empty());

        UserCryptoHolding holding = holdingService.getOrCreateHolding(user, crypto);

        assertNotNull(holding);
        assertEquals("USD", holding.getCurrency());
    }

    @Test
    void shouldGetExistingHolding() {
        UserCryptoHolding existing = new UserCryptoHolding();
        existing.setAmount(BigDecimal.ONE);

        when(holdingRepository.findByUserAndCryptoBasic(user, crypto)).thenReturn(Optional.of(existing));

        UserCryptoHolding result = holdingService.getOrCreateHolding(user, crypto);

        assertEquals(BigDecimal.ONE, result.getAmount());
    }

    @Test
    void shouldReturnAllHoldingsForUser() {
        UserCryptoHolding holding = new UserCryptoHolding();
        holding.setUser(user);
        holding.setCryptoBasic(crypto);
        holding.setCurrency("USD");

        when(holdingRepository.findByUserId(1L)).thenReturn(List.of(holding));

        List<UserCryptoHoldingDto> result = holdingService.getHoldingsForUser(1L);

        assertEquals(1, result.size());
    }

    @Test
    void shouldReturnAllHoldings() {
        UserCryptoHolding holding = new UserCryptoHolding();
        holding.setUser(user);
        holding.setCryptoBasic(crypto);
        holding.setCurrency("USD");
        holding.setCreatedAt(LocalDateTime.now());

        when(holdingRepository.findAll()).thenReturn(List.of(holding));

        List<UserCryptoHoldingDto> result = holdingService.getAllHoldings();

        assertEquals(1, result.size());
        assertEquals("USD", result.get(0).getCurrency());
    }
}