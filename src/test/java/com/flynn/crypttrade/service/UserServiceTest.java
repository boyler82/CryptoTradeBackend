package com.flynn.crypttrade.service;

import com.flynn.crypttrade.domain.NotificationSettings;
import com.flynn.crypttrade.domain.User;
import com.flynn.crypttrade.dto.UserDto;
import com.flynn.crypttrade.mapper.UserMapper;
import com.flynn.crypttrade.repository.NotificationSettingsRepository;
import com.flynn.crypttrade.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NotificationSettingsRepository notificationSettingsRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private PortfolioSnapshotService portfolioSnapshotService;

    private UserDto userDto;
    private User user;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder()
                .email("test@example.com")
                .passwordHash("plainpass")
                .build();

        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPasswordHash("hashedpass");
        user.setCreatedAt(LocalDateTime.now());
        user.setWalletBalance(BigDecimal.TEN);
    }

    @Test
    void shouldSaveUserSuccessfully() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userDto.getPasswordHash())).thenReturn("hashedpass");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto saved = userService.saveUser(userDto);

        assertEquals(user.getEmail(), saved.getEmail());
        verify(notificationSettingsRepository).save(any(NotificationSettings.class));
        verify(portfolioSnapshotService).createInitialSnapshot(any(User.class));
    }

    @Test
    void shouldThrowIfEmailExists() {
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class, () -> userService.saveUser(userDto));
    }

    @Test
    void shouldDeleteUserIfExists() {
        when(userRepository.getUserById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository).delete(user);
    }

    @Test
    void shouldThrowWhenDeletingNonExistentUser() {
        when(userRepository.getUserById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.deleteUser(1L));
    }

    @Test
    void shouldReturnUserDtoIfExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDto result = userService.getUser(1L);

        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUser(1L));
    }

    @Test
    void shouldReturnAllUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        assertEquals(1, userService.getAllUsers().size());
    }

    @Test
    void shouldReturnUserBalance() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertEquals(BigDecimal.TEN, userService.getUserBalance(1L));
    }

    @Test
    void shouldReturnZeroWhenUserBalanceNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertEquals(BigDecimal.ZERO, userService.getUserBalance(1L));
    }
}
