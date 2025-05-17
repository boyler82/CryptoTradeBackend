package com.flynn.crypttrade.service;

import com.flynn.crypttrade.domain.CryptoPriceHistory;
import com.flynn.crypttrade.dto.CryptoPriceHistoryDto;
import com.flynn.crypttrade.mapper.CryptoPriceHistoryMapper;
import com.flynn.crypttrade.repository.CryptoPriceHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoPriceHistoryService {

    private final CryptoPriceHistoryRepository cryptoPriceHistoryRepository;

    public CryptoPriceHistoryDto savePriceHistory(CryptoPriceHistoryDto dto) {
        CryptoPriceHistory entity = CryptoPriceHistoryMapper.toEntity(dto);
        entity.setTimestamp(LocalDateTime.now());
        CryptoPriceHistory saved = cryptoPriceHistoryRepository.save(entity);
        return CryptoPriceHistoryMapper.toDto(saved);
    }

    public List<CryptoPriceHistoryDto> getAllPriceHistory() {
        return cryptoPriceHistoryRepository.findAll().stream()
                .map(CryptoPriceHistoryMapper::toDto)
                .toList();
    }

    public List<CryptoPriceHistoryDto> getPriceHistoryForSymbol(String symbol) {
        return cryptoPriceHistoryRepository.findAllBySymbol(symbol).stream()
                .map(CryptoPriceHistoryMapper::toDto)
                .toList();
    }

    public CryptoPriceHistoryDto getLastPriceForSymbol(String symbol) {
        CryptoPriceHistory latest = cryptoPriceHistoryRepository.findTopBySymbolOrderByTimestampDesc(symbol)
                .orElseThrow(() -> new RuntimeException("No price history found for symbol: " + symbol));
        return CryptoPriceHistoryMapper.toDto(latest);
    }
}
