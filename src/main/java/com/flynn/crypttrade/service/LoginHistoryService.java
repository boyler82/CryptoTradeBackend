package com.flynn.crypttrade.service;

import com.flynn.crypttrade.domain.LoginHistory;
import com.flynn.crypttrade.domain.User;
import com.flynn.crypttrade.dto.LoginHistoryDto;
import com.flynn.crypttrade.mapper.LoginHistoryMapper;
import com.flynn.crypttrade.repository.LoginHistoryRepository;
import com.flynn.crypttrade.repository.UserRepository;
import com.flynn.crypttrade.util.IpUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginHistoryService {

    private final LoginHistoryRepository loginHistoryRepository;
    private final UserRepository userRepository;

    public LoginHistoryDto saveLoginHistory(LoginHistoryDto l, HttpServletRequest request) {
        l.setLoginTime(LocalDateTime.now());
        l.setIpAddress(IpUtils.getClientIp(request));

        LoginHistory entity = LoginHistoryMapper.toEntity(l);

        User user = userRepository.findById(l.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        entity.setUser(user);

        loginHistoryRepository.save(entity);
        return LoginHistoryMapper.toDto(entity);
    }

    public List<LoginHistoryDto> getAllLoginHistory() {
        return loginHistoryRepository.findAll()
                .stream()
                .map(LoginHistoryMapper::toDto)
                .toList();
    }

    public List<LoginHistoryDto> getLoginHistoryForUser(Long userId) {
        return loginHistoryRepository.findAllByUserId(userId)
                .stream()
                .map(LoginHistoryMapper::toDto)
                .toList();
    }

}
