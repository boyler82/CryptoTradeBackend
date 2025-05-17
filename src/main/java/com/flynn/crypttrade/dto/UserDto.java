package com.flynn.crypttrade.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;

    private String email;
    private String passwordHash;
    private LocalDateTime createdAt;
    private BigDecimal walletBalance;

    private List<Long> holdingsId;

    private List<Long> loginHistoryId;

    private Long notificationSettingsId;

    private List<Long> snapshotsId;
}
