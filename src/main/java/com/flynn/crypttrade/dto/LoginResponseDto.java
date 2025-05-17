package com.flynn.crypttrade.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginResponseDto {
    private Long userId;
    private String sessionToken;
}