package com.flynn.crypttrade.mapper;

import com.flynn.crypttrade.domain.ErrorLog;
import com.flynn.crypttrade.dto.ErrorLogDto;
import org.springframework.stereotype.Component;

@Component
public class ErrorLogMapper {

    public static ErrorLog toEntity(ErrorLogDto dto) {
        ErrorLog entity = new ErrorLog();
        entity.setId(dto.getId());
        entity.setSource(dto.getSource());
        entity.setMessage(dto.getMessage());
        entity.setTimestamp(dto.getTimestamp());
        return entity;
    }

    public static ErrorLogDto toDto(ErrorLog entity) {
        return ErrorLogDto.builder()
                .id(entity.getId())
                .source(entity.getSource())
                .message(entity.getMessage())
                .timestamp(entity.getTimestamp())
                .build();
    }
}
