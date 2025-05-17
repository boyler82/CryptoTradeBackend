package com.flynn.crypttrade.controller;

import com.flynn.crypttrade.dto.LoginHistoryDto;
import com.flynn.crypttrade.service.LoginHistoryService;
import com.flynn.crypttrade.util.IpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/login-history")
@RequiredArgsConstructor
public class LoginHistoryController {

    private final LoginHistoryService loginHistoryService;

    @PostMapping
    public ResponseEntity<LoginHistoryDto> saveLoginHistory(@RequestBody LoginHistoryDto dto, HttpServletRequest request) {
        LoginHistoryDto savedLogin = loginHistoryService.saveLoginHistory(dto, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLogin);
    }

    @GetMapping
    public ResponseEntity<List<LoginHistoryDto>> getAllLoginHistory() {
        List<LoginHistoryDto> history = loginHistoryService.getAllLoginHistory();
        return ResponseEntity.ok(history);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LoginHistoryDto>> getLoginHistoryForUser(@PathVariable Long userId) {
        List<LoginHistoryDto> history = loginHistoryService.getLoginHistoryForUser(userId);
        return ResponseEntity.ok(history);
    }
}
