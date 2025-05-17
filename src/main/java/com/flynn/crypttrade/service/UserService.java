package com.flynn.crypttrade.service;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.flynn.crypttrade.domain.*;
import com.flynn.crypttrade.dto.UserDto;
import com.flynn.crypttrade.mapper.UserMapper;
import com.flynn.crypttrade.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final NotificationSettingsRepository notificationSettingsRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PortfolioSnapshotService portfolioSnapshotService;



    @Transactional
    public UserDto saveUser(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        User entity = UserMapper.toEntity(userDto);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setWalletBalance(BigDecimal.valueOf(10)); // 10 USD na start
        String hashedPassword = passwordEncoder.encode(userDto.getPasswordHash());
        entity.setPasswordHash(hashedPassword);

        entity.setHoldings(new ArrayList<>());
        entity.setLoginHistory(new ArrayList<>());
        entity.setSnapshots(new ArrayList<>());
        entity.setWalletBalance(BigDecimal.valueOf(10));

        User saved = userRepository.save(entity);

        NotificationSettings settings = NotificationSettings.builder()
                .user(entity)
                .dailyReport(false)
                .weeklyReport(false)
                .preferredTime(LocalTime.of(12, 0))
                .build();
        notificationSettingsRepository.save(settings);


        portfolioSnapshotService.createInitialSnapshot(saved);

        return UserMapper.toDto(saved);
    }
    @Transactional
    public void deleteUser(Long userId){
        User user = userRepository.getUserById(userId)
                .orElseThrow(()-> new RuntimeException ("User not found."));
        userRepository.delete(user);
    }

    public UserDto getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));
        return UserMapper.toDto(user);
    }

    public List<UserDto> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    public BigDecimal getUserBalance(Long userId) {
        return userRepository.findById(userId)
                .map(User::getWalletBalance)
                .orElse(BigDecimal.ZERO);
    }
}
