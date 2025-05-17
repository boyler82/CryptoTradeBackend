package com.flynn.crypttrade.controller;

import com.flynn.crypttrade.domain.UserCryptoHolding;
import com.flynn.crypttrade.dto.UserCryptoHoldingDto;
import com.flynn.crypttrade.service.UserCryptoHoldingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/holding")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserCryptoHoldingController {

    private final UserCryptoHoldingService userCryptoHoldingService;


    @PostMapping
    public ResponseEntity<UserCryptoHoldingDto> addHolding(@RequestBody UserCryptoHoldingDto dto) {
        UserCryptoHolding savedHolding = userCryptoHoldingService.saveHolding(dto);
        UserCryptoHoldingDto responseDto = UserCryptoHoldingDto.builder()
                .id(savedHolding.getId())
                .userId(savedHolding.getUser().getId())
                .cryptoBasicId(savedHolding.getCryptoBasic().getExternalId())
                .amount(savedHolding.getAmount())
                .currency(savedHolding.getCurrency())
                .createdAt(savedHolding.getCreatedAt())
                .build();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserCryptoHoldingDto>> getAllHoldings() {
        List<UserCryptoHoldingDto> holdings = userCryptoHoldingService.getAllHoldings();
        return ResponseEntity.ok(holdings);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserCryptoHoldingDto>> getUserHoldings(@PathVariable Long userId) {
        List<UserCryptoHoldingDto> holdings = userCryptoHoldingService.getHoldingsForUser(userId);
        return ResponseEntity.ok(holdings);
    }
}