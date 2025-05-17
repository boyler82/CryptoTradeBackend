package com.flynn.crypttrade.service;

import com.flynn.crypttrade.domain.NotificationSettings;
import com.flynn.crypttrade.domain.User;
import com.flynn.crypttrade.dto.NotificationSettingsDto;
import com.flynn.crypttrade.repository.NotificationSettingsRepository;
import com.flynn.crypttrade.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationSettingsServiceTest {

    @InjectMocks
    private NotificationSettingsService service;

    @Mock
    private NotificationSettingsRepository notificationSettingsRepository;

    @Mock
    private UserRepository userRepository;

    private User user;
    private NotificationSettings settings;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        settings = new NotificationSettings();
        settings.setUser(user);
        settings.setDailyReport(false);
        settings.setWeeklyReport(true);
        settings.setPreferredTime(LocalTime.NOON);
        user.setNotificationSettings(settings);
    }

    @Test
    void shouldReturnNotificationSettingsDto() {
        when(notificationSettingsRepository.findByUserId(1L)).thenReturn(Optional.of(settings));

        NotificationSettingsDto dto = service.getSettingsForUser(1L);

        assertNotNull(dto);
        assertEquals(false, dto.getDailyReport());
        assertEquals(true, dto.getWeeklyReport());
        assertEquals(LocalTime.NOON, dto.getPreferredTime());
    }

    @Test
    void shouldThrowWhenSettingsNotFound() {
        when(notificationSettingsRepository.findByUserId(2L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.getSettingsForUser(2L));
    }

    @Test
    void shouldUpdateExistingSettings() {
        NotificationSettingsDto dto = NotificationSettingsDto.builder()
                .dailyReport(true)
                .weeklyReport(false)
                .preferredTime(LocalTime.of(9, 0))
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        service.updateSettings(1L, dto);

        assertTrue(settings.getDailyReport());
        assertFalse(settings.getWeeklyReport());
        assertEquals(LocalTime.of(9, 0), settings.getPreferredTime());
        verify(notificationSettingsRepository).save(settings);
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        NotificationSettingsDto dto = new NotificationSettingsDto();

        assertThrows(RuntimeException.class, () -> service.updateSettings(99L, dto));
    }
}