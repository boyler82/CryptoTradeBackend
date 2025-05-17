package com.flynn.crypttrade.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorLogDto {

    private Long id;

    private String source;
    private String message;
    private LocalDateTime timestamp;
}
