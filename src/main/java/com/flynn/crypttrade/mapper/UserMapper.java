package com.flynn.crypttrade.mapper;

import com.flynn.crypttrade.domain.User;
import com.flynn.crypttrade.dto.UserDto;

public class UserMapper {

    public static User toEntity(UserDto dto) {
        User entity = new User();
        entity.setId(dto.getId());
        entity.setEmail(dto.getEmail());
        entity.setWalletBalance(dto.getWalletBalance());
        entity.setPasswordHash(dto.getPasswordHash());
        entity.setCreatedAt(dto.getCreatedAt());
        return entity;
    };

    public static UserDto toDto(User entity) {
        return UserDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .passwordHash(entity.getPasswordHash())
                .walletBalance(entity.getWalletBalance())
                .createdAt(entity.getCreatedAt())
                .build();
    };
}
