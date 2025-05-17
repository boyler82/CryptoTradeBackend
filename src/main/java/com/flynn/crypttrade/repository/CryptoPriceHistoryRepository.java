package com.flynn.crypttrade.repository;

import com.flynn.crypttrade.domain.CryptoPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CryptoPriceHistoryRepository extends JpaRepository<CryptoPriceHistory, Long> {
    List<CryptoPriceHistory> findAllBySymbol(String symbol);

    Optional<CryptoPriceHistory> findTopBySymbolOrderByTimestampDesc(String symbol);
}
