package com.flynn.crypttrade.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto {

    private Long id;
    private String email;
    private LocalDateTime createdAt;
    private BigDecimal walletBalance;

    private List<UserCryptoHoldingDto> holdings;
    private List<LoginHistoryDto> loginHistory;
    private NotificationSettingsDto notificationSettings;
    private List<PortfolioSnapshotDto> snapshots;
}
