package com.flynn.crypttrade.controller;


import com.flynn.crypttrade.dto.WalletTransactionDto;
import com.flynn.crypttrade.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/buy")
    public ResponseEntity<String> buyCrypto(@RequestBody WalletTransactionDto dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("🔒 Logged user: " + (auth != null ? auth.getName() : "null"));
        System.out.println("📩 Otrzymano żądanie zakupu: " + dto);
        walletService.buyCrypto(dto);
        return ResponseEntity.ok("Crypto bought successfully!");
    }

    @PostMapping("/sell")
    public ResponseEntity<String> sellCrypto(@RequestBody WalletTransactionDto dto) {
        walletService.sellCrypto(dto);
        return ResponseEntity.ok("Crypto sold successfully!");
    }
}
