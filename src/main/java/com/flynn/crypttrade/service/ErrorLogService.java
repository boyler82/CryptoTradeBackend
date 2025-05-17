package com.flynn.crypttrade.service;

import com.flynn.crypttrade.domain.ErrorLog;
import com.flynn.crypttrade.dto.ErrorLogDto;
import com.flynn.crypttrade.mapper.ErrorLogMapper;
import com.flynn.crypttrade.repository.ErrorLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ErrorLogService {

    private final ErrorLogRepository errorLogRepository;

    public ErrorLogDto saveErrorLog(ErrorLogDto dto) {
        ErrorLog entity = ErrorLogMapper.toEntity(dto);
        entity.setTimestamp(LocalDateTime.now());
        ErrorLog saved = errorLogRepository.save(entity);
        return ErrorLogMapper.toDto(saved);
    }

    public List<ErrorLogDto> getAllErrors() {
        return errorLogRepository.findAll().stream()
                .map(ErrorLogMapper::toDto)
                .toList();
    }

    public List<ErrorLogDto> getErrorsBySource(String source) {
        return errorLogRepository.findAllBySource(source).stream()
                .map(ErrorLogMapper::toDto)
                .toList();
    }
}
