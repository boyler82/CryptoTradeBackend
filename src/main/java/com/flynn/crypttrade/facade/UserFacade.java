package com.flynn.crypttrade.facade;

import com.flynn.crypttrade.domain.User;
import com.flynn.crypttrade.dto.*;
import com.flynn.crypttrade.repository.UserRepository;
import com.flynn.crypttrade.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserRepository userRepository;
    private final UserCryptoHoldingService holdingService;
    private final LoginHistoryService loginHistoryService;
    private final NotificationSettingsService notificationSettingsService;
    private final PortfolioSnapshotService portfolioSnapshotService;

    public UserDetailsDto getUserDetails(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Użytkownik nie istnieje"));

        return UserDetailsDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .walletBalance(user.getWalletBalance())
                .holdings(holdingService.getHoldingsForUser(userId))
                .loginHistory(loginHistoryService.getLoginHistoryForUser(userId))
                .notificationSettings(notificationSettingsService.getSettingsForUser(userId))
                .snapshots(portfolioSnapshotService.getSnapshotsForUser(userId))
                .build();
    }
}