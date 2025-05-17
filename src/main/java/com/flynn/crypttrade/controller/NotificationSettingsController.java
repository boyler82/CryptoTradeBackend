package com.flynn.crypttrade.controller;

import com.flynn.crypttrade.dto.NotificationSettingsDto;
import com.flynn.crypttrade.service.NotificationSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationSettingsController {

    private final NotificationSettingsService notificationSettingsService;

    @PatchMapping("/{userId}")
    public ResponseEntity<NotificationSettingsDto> updateSettings(
            @PathVariable Long userId,
            @RequestBody NotificationSettingsDto dto
    ) {
        dto.setUserId(userId);
        notificationSettingsService.updateSettings(userId, dto);
        return ResponseEntity.ok(dto);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<NotificationSettingsDto> getSettings(@PathVariable Long userId) {
        NotificationSettingsDto settings = notificationSettingsService.getSettingsForUser(userId);
        return ResponseEntity.ok(settings);
    }
}