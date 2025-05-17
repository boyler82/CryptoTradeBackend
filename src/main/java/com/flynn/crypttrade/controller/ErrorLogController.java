package com.flynn.crypttrade.controller;

import com.flynn.crypttrade.dto.ErrorLogDto;
import com.flynn.crypttrade.service.ErrorLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/error-log")
@RequiredArgsConstructor
public class ErrorLogController {

    private final ErrorLogService errorLogService;

    @PostMapping
    public ResponseEntity<ErrorLogDto> saveError(@RequestBody ErrorLogDto dto) {
        ErrorLogDto saved = errorLogService.saveErrorLog(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<ErrorLogDto>> getAllErrors() {
        List<ErrorLogDto> errors = errorLogService.getAllErrors();
        return ResponseEntity.ok(errors);
    }

    @GetMapping("/source/{source}")
    public ResponseEntity<List<ErrorLogDto>> getErrorsBySource(@PathVariable String source) {
        List<ErrorLogDto> errors = errorLogService.getErrorsBySource(source);
        return ResponseEntity.ok(errors);
    }
}