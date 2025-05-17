package com.flynn.crypttrade.controller;

import com.flynn.crypttrade.dto.CryptoPriceHistoryDto;
import com.flynn.crypttrade.service.CryptoPriceHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crypto-price")
@RequiredArgsConstructor
public class CryptoPriceHistoryController {

    private final CryptoPriceHistoryService cryptoPriceHistoryService;

    @PostMapping
    public ResponseEntity<CryptoPriceHistoryDto> savePrice(@RequestBody CryptoPriceHistoryDto dto) {
        CryptoPriceHistoryDto saved = cryptoPriceHistoryService.savePriceHistory(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<CryptoPriceHistoryDto>> getAllPrices() {
        List<CryptoPriceHistoryDto> prices = cryptoPriceHistoryService.getAllPriceHistory();
        return ResponseEntity.ok(prices);
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<List<CryptoPriceHistoryDto>> getPriceHistoryForSymbol(@PathVariable String symbol) {
        List<CryptoPriceHistoryDto> history = cryptoPriceHistoryService.getPriceHistoryForSymbol(symbol);
        System.out.println(">>> Odczyt historii dla symbolu: " + symbol);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/{symbol}/last")
    public ResponseEntity<CryptoPriceHistoryDto> getLastPriceForSymbol(@PathVariable String symbol) {
        CryptoPriceHistoryDto lastPrice = cryptoPriceHistoryService.getLastPriceForSymbol(symbol);
        return ResponseEntity.ok(lastPrice);
    }
}
