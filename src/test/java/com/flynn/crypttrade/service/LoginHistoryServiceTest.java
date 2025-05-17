package com.flynn.crypttrade.service;

import com.flynn.crypttrade.domain.LoginHistory;
import com.flynn.crypttrade.domain.User;
import com.flynn.crypttrade.dto.LoginHistoryDto;
import com.flynn.crypttrade.mapper.LoginHistoryMapper;
import com.flynn.crypttrade.repository.LoginHistoryRepository;
import com.flynn.crypttrade.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginHistoryServiceTest {

    @InjectMocks
    private LoginHistoryService loginHistoryService;

    @Mock
    private LoginHistoryRepository loginHistoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpServletRequest request;

    private User user;
    private LoginHistoryDto dto;

    @BeforeEach
    void setup() {
        user = new User();
        user.setId(1L);

        dto = new LoginHistoryDto();
        dto.setUserId(1L);
    }

    @Test
    void shouldSaveLoginHistory() {
        when(request.getHeader("X-Forwarded-For")).thenReturn(null);
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(loginHistoryRepository.save(any(LoginHistory.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        LoginHistoryDto result = loginHistoryService.saveLoginHistory(dto, request);

        assertNotNull(result);
        assertEquals("127.0.0.1", result.getIpAddress());
        assertEquals(1L, result.getUserId());
    }

    @Test
    void shouldGetAllLoginHistory() {
        LoginHistory history = new LoginHistory();
        history.setUser(user); // ← dodane!
        when(loginHistoryRepository.findAll()).thenReturn(List.of(history));

        List<LoginHistoryDto> result = loginHistoryService.getAllLoginHistory();
        assertEquals(1, result.size());
    }

    @Test
    void shouldGetLoginHistoryForUser() {
        LoginHistory history = new LoginHistory();
        history.setUser(user); // ← dodane!
        when(loginHistoryRepository.findAllByUserId(1L)).thenReturn(List.of(history));

        List<LoginHistoryDto> result = loginHistoryService.getLoginHistoryForUser(1L);
        assertEquals(1, result.size());
    }
}