package com.flynn.crypttrade.mapper;


import com.flynn.crypttrade.domain.UserCryptoHolding;
import com.flynn.crypttrade.dto.UserCryptoHoldingDto;
import org.springframework.stereotype.Component;

@Component
public class UserCryptoHoldingMapper {

    public static UserCryptoHolding toEntity(UserCryptoHoldingDto dto) {
        UserCryptoHolding entity = new UserCryptoHolding();
        entity.setId(dto.getId());
        entity.setAmount(dto.getAmount());
        entity.setCurrency(dto.getCurrency());
        return entity;
    }

    public static UserCryptoHoldingDto toDto(UserCryptoHolding entity) {
        if (entity == null) {
            return null;
        }

        return UserCryptoHoldingDto.builder()
                .id(entity.getId())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .cryptoBasicId(entity.getCryptoBasic() != null ? entity.getCryptoBasic().getExternalId() : null) // poprawka tu!
                .amount(entity.getAmount())
                .currency(entity.getCurrency())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}