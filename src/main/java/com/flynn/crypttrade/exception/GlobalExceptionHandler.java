package com.flynn.crypttrade.exception;

import com.flynn.crypttrade.dto.ErrorLogDto;
import com.flynn.crypttrade.service.ErrorLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ErrorLogService errorLogService;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex) {
        ErrorLogDto errorLog = ErrorLogDto.builder()
                .source("GlobalExceptionHandler")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        errorLogService.saveErrorLog(errorLog);

        Map<String, Object> response = new HashMap<>();
        response.put("error", "Internal Server Error");
        response.put("message", ex.getMessage());
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}