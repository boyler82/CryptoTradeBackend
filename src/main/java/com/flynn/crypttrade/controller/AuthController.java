package com.flynn.crypttrade.controller;

import com.flynn.crypttrade.dto.LoginHistoryDto;
import com.flynn.crypttrade.service.LoginHistoryService;
import com.flynn.crypttrade.service.PortfolioSnapshotService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.time.Duration;
import java.util.UUID;
import com.flynn.crypttrade.domain.User;
import com.flynn.crypttrade.dto.LoginRequest;
import com.flynn.crypttrade.dto.LoginResponseDto;
import com.flynn.crypttrade.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final LoginHistoryService loginHistoryService;
    private final PortfolioSnapshotService portfolioSnapshotService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequest request, HttpSession session, HttpServletRequest httpRequest) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }

        LoginHistoryDto historyDto = LoginHistoryDto.builder()
                .userId(user.getId())
                .build();

        loginHistoryService.saveLoginHistory(historyDto, httpRequest);

        String sessionToken = UUID.randomUUID().toString();

        session.setAttribute("userId", user.getId());
        session.setAttribute("sessionToken", sessionToken);

        ResponseCookie sessionCookie = ResponseCookie.from("sessionToken", sessionToken)
                .httpOnly(true)
                .sameSite("Lax")
                .secure(false) // zmień na true przy HTTPS
                .path("/")
                .maxAge(Duration.ofDays(1))
                .build();

        portfolioSnapshotService.createSnapshotForUser(user);

        LoginResponseDto dto = new LoginResponseDto(user.getId(), sessionToken);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, sessionCookie.toString())
                .body(dto);
    }
}