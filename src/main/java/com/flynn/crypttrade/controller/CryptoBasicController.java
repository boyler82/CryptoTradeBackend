package com.flynn.crypttrade.controller;

import com.flynn.crypttrade.domain.CryptoBasic;
import com.flynn.crypttrade.dto.CryptoBasicDto;
import com.flynn.crypttrade.service.CryptoBasicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crypto-basic")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CryptoBasicController {

    private final CryptoBasicService cryptoBasicService;

    @PostMapping("/{cryptoId}")
    public ResponseEntity<CryptoBasic> addCryptoBasic(@PathVariable String cryptoId) {
        CryptoBasic crypto = cryptoBasicService.saveCrypto(cryptoId);
        return ResponseEntity.ok(crypto);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CryptoBasicDto>> getAllCryptos() {
        List<CryptoBasicDto> cryptos = cryptoBasicService.getAllCryptos();
        return ResponseEntity.ok(cryptos);
    }

    @PostMapping("/import")
    public ResponseEntity<Void> importAllCryptos() {
        cryptoBasicService.fetchAllCryptos();
        return ResponseEntity.ok().build();
    }
}
