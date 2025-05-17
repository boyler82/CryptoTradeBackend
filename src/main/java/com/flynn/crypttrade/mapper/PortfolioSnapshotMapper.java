package com.flynn.crypttrade.mapper;

import com.flynn.crypttrade.domain.PortfolioSnapshot;
import com.flynn.crypttrade.dto.PortfolioSnapshotDto;
import org.springframework.stereotype.Component;

@Component
public class PortfolioSnapshotMapper {

    public PortfolioSnapshotDto toDto(PortfolioSnapshot snapshot) {
        return PortfolioSnapshotDto.builder()
                .id(snapshot.getId())
                .fiatValue(snapshot.getFiatValue())
                .cryptoValue(snapshot.getCryptoValue())
                .totalValue(snapshot.getTotalValue())
                .currency(snapshot.getCurrency())
                .snapshotDate(snapshot.getSnapshotDate())
                .build();
    }

    public static PortfolioSnapshot toEntity(PortfolioSnapshotDto dto){
      PortfolioSnapshot entity = new PortfolioSnapshot();
      entity.setId(dto.getId());
      entity.setFiatValue(dto.getFiatValue());
      entity.setCryptoValue(dto.getCryptoValue());
      entity.setTotalValue(dto.getTotalValue());
      entity.setCurrency(dto.getCurrency());
      entity.setSnapshotDate(dto.getSnapshotDate());
      return entity;
    }

}
