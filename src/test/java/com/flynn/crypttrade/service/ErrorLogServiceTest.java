package com.flynn.crypttrade.service;

import com.flynn.crypttrade.domain.ErrorLog;
import com.flynn.crypttrade.dto.ErrorLogDto;
import com.flynn.crypttrade.mapper.ErrorLogMapper;
import com.flynn.crypttrade.repository.ErrorLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ErrorLogServiceTest {

    @Mock
    private ErrorLogRepository errorLogRepository;

    @InjectMocks
    private ErrorLogService errorLogService;

    private ErrorLogDto errorLogDto;
    private ErrorLog errorLog;

    @BeforeEach
    void setup() {
        errorLogDto = new ErrorLogDto(null, "sourceA", "Something went wrong", null);
        errorLog = new ErrorLog();
        errorLog.setId(1L);
        errorLog.setSource("sourceA");
        errorLog.setMessage("Something went wrong");
        errorLog.setTimestamp(LocalDateTime.now());
    }

    @Test
    void shouldSaveErrorLog() {
        when(errorLogRepository.save(any(ErrorLog.class))).thenReturn(errorLog);

        ErrorLogDto result = errorLogService.saveErrorLog(errorLogDto);

        assertNotNull(result);
        assertEquals("sourceA", result.getSource());
        assertEquals("Something went wrong", result.getMessage());
        verify(errorLogRepository, times(1)).save(any(ErrorLog.class));
    }

    @Test
    void shouldGetAllErrors() {
        when(errorLogRepository.findAll()).thenReturn(List.of(errorLog));

        List<ErrorLogDto> result = errorLogService.getAllErrors();

        assertEquals(1, result.size());
        assertEquals("sourceA", result.get(0).getSource());
        verify(errorLogRepository, times(1)).findAll();
    }

    @Test
    void shouldGetErrorsBySource() {
        when(errorLogRepository.findAllBySource("sourceA")).thenReturn(List.of(errorLog));

        List<ErrorLogDto> result = errorLogService.getErrorsBySource("sourceA");

        assertEquals(1, result.size());
        assertEquals("sourceA", result.get(0).getSource());
        verify(errorLogRepository, times(1)).findAllBySource("sourceA");
    }
}