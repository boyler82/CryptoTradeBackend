package com.flynn.crypttrade.mapper;

import com.flynn.crypttrade.domain.PortfolioSnapshot;
import com.flynn.crypttrade.dto.PortfolioSnapshotDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PortfolioSnapshotMapperTest {

    private final PortfolioSnapshotMapper mapper = new PortfolioSnapshotMapper();

    @Test
    void shouldMapEntityToDto() {
        PortfolioSnapshot entity = new PortfolioSnapshot();
        entity.setId(1L);
        entity.setFiatValue(BigDecimal.valueOf(100));
        entity.setCryptoValue(BigDecimal.valueOf(200));
        entity.setTotalValue(BigDecimal.valueOf(300));
        entity.setCurrency("USD");
        entity.setSnapshotDate(LocalDate.of(2025, 5, 6));

        PortfolioSnapshotDto dto = mapper.toDto(entity);

        assertEquals(1L, dto.getId());
        assertEquals(BigDecimal.valueOf(100), dto.getFiatValue());
        assertEquals(BigDecimal.valueOf(200), dto.getCryptoValue());
        assertEquals(BigDecimal.valueOf(300), dto.getTotalValue());
        assertEquals("USD", dto.getCurrency());
        assertEquals(LocalDate.of(2025, 5, 6), dto.getSnapshotDate());
    }

    @Test
    void shouldMapDtoToEntity() {
        PortfolioSnapshotDto dto = PortfolioSnapshotDto.builder()
                .id(2L)
                .fiatValue(BigDecimal.valueOf(10))
                .cryptoValue(BigDecimal.valueOf(20))
                .totalValue(BigDecimal.valueOf(30))
                .currency("EUR")
                .snapshotDate(LocalDate.of(2025, 1, 1))
                .build();

        PortfolioSnapshot entity = PortfolioSnapshotMapper.toEntity(dto);

        assertEquals(2L, entity.getId());
        assertEquals(BigDecimal.valueOf(10), entity.getFiatValue());
        assertEquals(BigDecimal.valueOf(20), entity.getCryptoValue());
        assertEquals(BigDecimal.valueOf(30), entity.getTotalValue());
        assertEquals("EUR", entity.getCurrency());
        assertEquals(LocalDate.of(2025, 1, 1), entity.getSnapshotDate());
    }
}