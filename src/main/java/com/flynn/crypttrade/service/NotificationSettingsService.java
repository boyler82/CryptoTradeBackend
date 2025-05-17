package com.flynn.crypttrade.service;

import com.flynn.crypttrade.domain.NotificationSettings;
import com.flynn.crypttrade.domain.User;
import com.flynn.crypttrade.dto.NotificationSettingsDto;
import com.flynn.crypttrade.mapper.NotificationSettingsMapper;
import com.flynn.crypttrade.repository.NotificationSettingsRepository;
import com.flynn.crypttrade.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class NotificationSettingsService {

    private final NotificationSettingsRepository notificationSettingsRepository;
    private final UserRepository userRepository;

    public NotificationSettingsDto getSettingsForUser(Long userId) {
        NotificationSettings settings = notificationSettingsRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Brak ustawień powiadomień"));
        return NotificationSettingsMapper.toDto(settings);
    }

    public void updateSettings(Long userId, NotificationSettingsDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        NotificationSettings settings = user.getNotificationSettings();
        if (settings == null) {
            settings = new NotificationSettings();
            settings.setUser(user);
        }

        if (dto.getDailyReport() != null) {
            settings.setDailyReport(dto.getDailyReport());
        }

        if (dto.getWeeklyReport() != null) {
            settings.setWeeklyReport(dto.getWeeklyReport());
        }

        if (dto.getPreferredTime() != null) {
            settings.setPreferredTime(dto.getPreferredTime());
        }

        notificationSettingsRepository.save(settings);
    }
}
