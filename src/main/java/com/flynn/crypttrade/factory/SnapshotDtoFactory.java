package com.flynn.crypttrade.factory;
import com.flynn.crypttrade.domain.User;
import com.flynn.crypttrade.dto.PortfolioSnapshotDto;

public interface SnapshotDtoFactory {
    PortfolioSnapshotDto createFromUser(User user, String currency);
}
