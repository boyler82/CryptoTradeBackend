package com.flynn.crypttrade.service;

import com.flynn.crypttrade.domain.CryptoBasic;
import com.flynn.crypttrade.domain.User;
import com.flynn.crypttrade.domain.UserCryptoHolding;
import com.flynn.crypttrade.dto.UserCryptoHoldingDto;
import com.flynn.crypttrade.mapper.UserCryptoHoldingMapper;
import com.flynn.crypttrade.repository.CryptoBasicRepository;
import com.flynn.crypttrade.repository.UserCryptoHoldingRepository;
import com.flynn.crypttrade.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserCryptoHoldingService {

    private final UserCryptoHoldingRepository userCryptoHoldingRepository;
    private final UserRepository userRepository;
    private final CryptoBasicRepository cryptoBasicRepository;

    public UserCryptoHolding saveHolding(UserCryptoHoldingDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        CryptoBasic cryptoBasic = cryptoBasicRepository.findByExternalId(dto.getCryptoBasicId())
                .orElseThrow(() -> new RuntimeException("CryptoBasic not found"));

        UserCryptoHolding holding = new UserCryptoHolding();
        holding.setUser(user);
        holding.setCryptoBasic(cryptoBasic);
        holding.setAmount(dto.getAmount());
        holding.setCurrency(dto.getCurrency());
        holding.setCreatedAt(LocalDateTime.now());

        return userCryptoHoldingRepository.save(holding);
    }

    public UserCryptoHolding getOrCreateHolding(User user, CryptoBasic crypto) {
        return userCryptoHoldingRepository.findByUserAndCryptoBasic(user, crypto)
                .orElseGet(() -> {
                    UserCryptoHolding holding = new UserCryptoHolding();
                    holding.setUser(user);
                    holding.setCryptoBasic(crypto);
                    holding.setCurrency("USD");
                    holding.setCreatedAt(LocalDateTime.now());
                    return holding;
                });
    }

    public void saveHolding(UserCryptoHolding holding) {
        userCryptoHoldingRepository.save(holding);
    }

    public List<UserCryptoHoldingDto> getHoldingsForUser(Long userId) {
        return userCryptoHoldingRepository.findByUserId(userId)
                .stream()
                .map(UserCryptoHoldingMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<UserCryptoHoldingDto> getAllHoldings() {
        return userCryptoHoldingRepository.findAll().stream()
                .map(holding -> UserCryptoHoldingDto.builder()
                        .id(holding.getId())
                        .userId(holding.getUser().getId())
                        .cryptoBasicId(holding.getCryptoBasic().getExternalId())
                        .amount(holding.getAmount())
                        .currency(holding.getCurrency())
                        .createdAt(holding.getCreatedAt())
                        .build())
                .toList();
    }
}