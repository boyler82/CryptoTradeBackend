package com.flynn.crypttrade.scheduler;

import com.flynn.crypttrade.domain.NotificationSettings;
import com.flynn.crypttrade.domain.PortfolioSnapshot;
import com.flynn.crypttrade.domain.User;
import com.flynn.crypttrade.repository.PortfolioSnapshotRepository;
import com.flynn.crypttrade.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SnapshotScheduler {

    private final UserRepository userRepository;
    private final PortfolioSnapshotRepository snapshotRepository;

    @Scheduled(cron = "0 0 0 * * *") // Codziennie o północy
    public void generateDailySnapshots() {
        log.info("🔄 Start generating daily snapshots");

        List<User> users = userRepository.findAll();

        for (User user : users) {
            if (user.getNotificationSettings() != null && Boolean.TRUE.equals(user.getNotificationSettings().getDailyReport())) {
                BigDecimal totalValue = calculateTotalPortfolioValue(user);
                PortfolioSnapshot snapshot = PortfolioSnapshot.builder()
                        .user(user)
                        .totalValue(totalValue)
                        .currency("USD")
                        .snapshotDate(LocalDate.now())
                        .build();

                snapshotRepository.save(snapshot);
                log.info("✅ Snapshot created for user: {}", user.getEmail());
            }
        }

        log.info("🎯 All daily snapshots done");
    }

    private BigDecimal calculateTotalPortfolioValue(User user) {
        return user.getHoldings().stream()
                .map(h -> {
                    BigDecimal price = h.getCryptoBasic().getCurrentPrice() != null
                            ? h.getCryptoBasic().getCurrentPrice()
                            : BigDecimal.ZERO;
                    BigDecimal amount = h.getAmount() != null ? h.getAmount() : BigDecimal.ZERO;
                    return price.multiply(amount);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
