package ru.vasilev.wallet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vasilev.wallet.exception.InsufficientFundsException;
import ru.vasilev.wallet.exception.WalletNotFoundException;
import ru.vasilev.wallet.domain.Wallet;
import ru.vasilev.wallet.service.WalletService;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class WalletController {
    private WalletService walletService;

    public WalletController(WalletService walletService){
        this.walletService = walletService;
    }

    @PostMapping("/wallet")
    public ResponseEntity<?> updateWallet(@RequestBody OrderInfo request){
        try{
            Wallet updatedWallet = walletService.edit(
                    request.getWalletId(), request.getOperation(), request.getAmount()
            );
            return ResponseEntity.ok(updatedWallet);
        }catch(WalletNotFoundException | InsufficientFundsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/wallets/{walletId}")
    public ResponseEntity<?> getWalletBalance(@PathVariable UUID walletId){
        try{
            BigDecimal balance = walletService.getWalletBalance(walletId);
            return ResponseEntity.ok(balance);
        }catch(WalletNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}