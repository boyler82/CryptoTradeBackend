package com.flynn.crypttrade.facade;


import com.flynn.crypttrade.domain.User;
import com.flynn.crypttrade.dto.*;
import com.flynn.crypttrade.repository.UserRepository;
import com.flynn.crypttrade.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserFacadeTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserCryptoHoldingService holdingService;
    @Mock
    private LoginHistoryService loginHistoryService;
    @Mock
    private NotificationSettingsService notificationSettingsService;
    @Mock
    private PortfolioSnapshotService snapshotService;

    @InjectMocks
    private UserFacade userFacade;

    private User user;

    @BeforeEach
    void setup() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setCreatedAt(LocalDateTime.now());
        user.setWalletBalance(BigDecimal.TEN);
    }

    @Test
    void shouldReturnUserDetails() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(holdingService.getHoldingsForUser(1L)).thenReturn(Collections.emptyList());
        when(loginHistoryService.getLoginHistoryForUser(1L)).thenReturn(Collections.emptyList());
        when(notificationSettingsService.getSettingsForUser(1L)).thenReturn(new NotificationSettingsDto());
        when(snapshotService.getSnapshotsForUser(1L)).thenReturn(Collections.emptyList());

        UserDetailsDto dto = userFacade.getUserDetails(1L);

        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getWalletBalance(), dto.getWalletBalance());
        assertNotNull(dto.getNotificationSettings());
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userFacade.getUserDetails(1L));
    }
}