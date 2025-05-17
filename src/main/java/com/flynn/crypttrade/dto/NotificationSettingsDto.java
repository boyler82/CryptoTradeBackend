package com.flynn.crypttrade.dto;

import lombok.*;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationSettingsDto {

    private Long id;

    private Long userId;

    private Boolean dailyReport;
    private Boolean weeklyReport;
    private LocalTime preferredTime;
}
