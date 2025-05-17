package com.flynn.crypttrade.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "error_log")
public class ErrorLog {
    @Id
    @GeneratedValue
    private Long id;

    private String source;
    private String message;
    private LocalDateTime timestamp;
}
