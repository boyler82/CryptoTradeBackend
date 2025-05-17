package com.flynn.crypttrade.mapper;

import com.flynn.crypttrade.domain.LoginHistory;
import com.flynn.crypttrade.dto.LoginHistoryDto;
import org.springframework.stereotype.Component;

@Component
public class LoginHistoryMapper {

    public static LoginHistory toEntity(LoginHistoryDto dto) {
       LoginHistory entity = new LoginHistory();
       entity.setId(dto.getId());
       entity.setLoginTime(dto.getLoginTime());
       entity.setIpAddress(dto.getIpAddress());
       return entity;
    }
    public static LoginHistoryDto toDto(LoginHistory entity) {
        return LoginHistoryDto.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .loginTime(entity.getLoginTime())
                .ipAddress(entity.getIpAddress())
                .build();
    }
}
