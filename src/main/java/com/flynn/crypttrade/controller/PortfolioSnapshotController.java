package com.flynn.crypttrade.controller;

import com.flynn.crypttrade.domain.PortfolioSnapshot;
import com.flynn.crypttrade.dto.PortfolioSnapshotDto;
import com.flynn.crypttrade.mapper.PortfolioSnapshotMapper;
import com.flynn.crypttrade.service.PortfolioSnapshotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/snapshots")
@RequiredArgsConstructor
public class PortfolioSnapshotController {

    private final PortfolioSnapshotService portfolioSnapshotService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PortfolioSnapshotDto>> getSnapshots(@PathVariable Long userId) {
        return ResponseEntity.ok(portfolioSnapshotService.getSnapshotsForUser(userId));
    }


    @GetMapping("/current/{userId}")
    public ResponseEntity<PortfolioSnapshotDto> getLiveSnapshot(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "USD") String currency
    ) {
        return ResponseEntity.ok(portfolioSnapshotService.getLiveSnapshotForUser(userId, currency));
    }
}