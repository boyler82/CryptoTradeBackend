package com.flynn.crypttrade.service;

import com.flynn.crypttrade.domain.PortfolioSnapshot;
import com.flynn.crypttrade.domain.User;
import com.flynn.crypttrade.dto.PortfolioSnapshotDto;
import com.flynn.crypttrade.factory.SnapshotDtoFactory;
import com.flynn.crypttrade.mapper.PortfolioSnapshotMapper;
import com.flynn.crypttrade.repository.PortfolioSnapshotRepository;
import com.flynn.crypttrade.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioSnapshotService {

    private final PortfolioSnapshotRepository portfolioSnapshotRepository;
    private final UserRepository userRepository;
    private final PortfolioSnapshotMapper portfolioSnapshotMapper;
    private final SnapshotDtoFactory snapshotDtoFactory;

    public void createInitialSnapshot(User user) {
        PortfolioSnapshot snapshot = PortfolioSnapshot.builder()
                .user(user)
                .totalValue(BigDecimal.valueOf(10))
                .fiatValue(BigDecimal.valueOf(10))
                .cryptoValue(BigDecimal.ZERO)
                .currency("USD")
                .snapshotDate(LocalDate.now())
                .build();

        portfolioSnapshotRepository.save(snapshot);
    }

    public void createSnapshotForUser(User user) {
        BigDecimal walletBalance = user.getWalletBalance() != null ? user.getWalletBalance() : BigDecimal.ZERO;

        BigDecimal cryptoValue = user.getHoldings().stream()
                .map(h -> {
                    BigDecimal price = h.getCryptoBasic().getCurrentPrice() != null
                            ? h.getCryptoBasic().getCurrentPrice()
                            : BigDecimal.ZERO;
                    BigDecimal amount = h.getAmount() != null ? h.getAmount() : BigDecimal.ZERO;
                    return price.multiply(amount);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal total = walletBalance.add(cryptoValue);

        PortfolioSnapshot snapshot = PortfolioSnapshot.builder()
                .user(user)
                .currency("USD")
                .fiatValue(walletBalance)
                .cryptoValue(cryptoValue)
                .totalValue(total)
                .snapshotDate(LocalDate.now())
                .build();

        portfolioSnapshotRepository.save(snapshot);
    }

    public List<PortfolioSnapshotDto> getSnapshotsForUser(Long userId) {
        try {
            List<PortfolioSnapshot> snapshots = portfolioSnapshotRepository.findAllByUserId(userId);
            return snapshots.stream()
                    .map(portfolioSnapshotMapper::toDto)
                    .toList();
        } catch (Exception e) {
            System.err.println("❌ Błąd w getSnapshotsForUser: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
    public PortfolioSnapshotDto getLiveSnapshotForUser(Long userId, String currency) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Użytkownik nie istnieje"));

        return snapshotDtoFactory.createFromUser(user, currency);
    }
}