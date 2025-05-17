package com.flynn.crypttrade.mapper;

import com.flynn.crypttrade.domain.NotificationSettings;
import com.flynn.crypttrade.dto.NotificationSettingsDto;

public class NotificationSettingsMapper {

    public static NotificationSettingsDto toDto(NotificationSettings settings) {
        return NotificationSettingsDto.builder()
                .userId(settings.getUser().getId())
                .dailyReport(settings.getDailyReport())
                .weeklyReport(settings.getWeeklyReport())
                .preferredTime(settings.getPreferredTime())
                .build();
    }

    public static void updateFromDto(NotificationSettings settings, NotificationSettingsDto dto) {
        settings.setDailyReport(dto.getDailyReport());
        settings.setWeeklyReport(dto.getWeeklyReport());
        settings.setPreferredTime(dto.getPreferredTime());
    }
}


