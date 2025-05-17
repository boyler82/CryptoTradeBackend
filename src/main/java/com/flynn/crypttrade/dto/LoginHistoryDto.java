package com.flynn.crypttrade.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginHistoryDto {

    private Long id;


    private Long userId;

    private LocalDateTime loginTime;
    private String ipAddress;
}