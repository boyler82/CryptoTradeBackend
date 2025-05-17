package com.flynn.crypttrade.service;

import com.flynn.crypttrade.domain.CryptoBasic;
import com.flynn.crypttrade.domain.User;
import com.flynn.crypttrade.domain.UserCryptoHolding;
import com.flynn.crypttrade.dto.PortfolioSnapshotDto;
import com.flynn.crypttrade.factory.SnapshotDtoFactory;
import com.flynn.crypttrade.mapper.PortfolioSnapshotMapper;
import com.flynn.crypttrade.repository.PortfolioSnapshotRepository;
import com.flynn.crypttrade.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PortfolioSnapshotServiceTest {

    @Mock
    private PortfolioSnapshotRepository snapshotRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PortfolioSnapshotMapper snapshotMapper;
    @Mock
    private SnapshotDtoFactory snapshotDtoFactory;

    @InjectMocks
    private PortfolioSnapshotService snapshotService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setWalletBalance(BigDecimal.TEN);
        user.setHoldings(List.of());
    }

    @Test
    void shouldCreateInitialSnapshot() {
        snapshotService.createInitialSnapshot(user);
        verify(snapshotRepository, times(1)).save(argThat(snapshot ->
                snapshot.getUser().equals(user) &&
                        snapshot.getTotalValue().equals(BigDecimal.TEN) &&
                        snapshot.getFiatValue().equals(BigDecimal.TEN) &&
                        snapshot.getCryptoValue().equals(BigDecimal.ZERO) &&
                        snapshot.getCurrency().equals("USD") &&
                        snapshot.getSnapshotDate().equals(LocalDate.now())
        ));
    }

    @Test
    void shouldCreateSnapshotForUserWithHoldings() {
        CryptoBasic crypto = new CryptoBasic();
        crypto.setCurrentPrice(BigDecimal.valueOf(100));
        UserCryptoHolding holding = new UserCryptoHolding();
        holding.setCryptoBasic(crypto);
        holding.setAmount(BigDecimal.valueOf(2));
        user.setHoldings(List.of(holding));

        snapshotService.createSnapshotForUser(user);

        verify(snapshotRepository, times(1)).save(argThat(snapshot ->
                snapshot.getFiatValue().equals(user.getWalletBalance()) &&
                        snapshot.getCryptoValue().equals(BigDecimal.valueOf(200)) &&
                        snapshot.getTotalValue().equals(BigDecimal.valueOf(210))
        ));
    }

    @Test
    void shouldReturnSnapshotDtoList() {
        when(snapshotRepository.findAllByUserId(1L)).thenReturn(List.of());

        List<PortfolioSnapshotDto> result = snapshotService.getSnapshotsForUser(1L);

        assertNotNull(result);
        verify(snapshotRepository).findAllByUserId(1L);
    }

    @Test
    void shouldReturnLiveSnapshot() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        PortfolioSnapshotDto dto = new PortfolioSnapshotDto();
        when(snapshotDtoFactory.createFromUser(user, "USD")).thenReturn(dto);

        PortfolioSnapshotDto result = snapshotService.getLiveSnapshotForUser(1L, "USD");

        assertEquals(dto, result);
    }

    @Test
    void shouldThrowWhenUserNotFoundInLiveSnapshot() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> snapshotService.getLiveSnapshotForUser(1L, "USD"));
    }
}
