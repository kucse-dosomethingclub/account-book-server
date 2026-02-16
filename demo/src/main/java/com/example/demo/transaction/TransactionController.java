package com.example.demo.transaction;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trans")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/new")
    public ResponseEntity<String> newTransaction(@RequestBody TransactionDto.transactionRequest requestDto, @AuthenticationPrincipal String email) {
        transactionService.saveTransaction(requestDto, email);
        return ResponseEntity.status(HttpStatus.CREATED).body("가계부 내역이 저장되었습니다.");
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto.transactionResponse>> getTransactions(@AuthenticationPrincipal String email) {
        List<TransactionDto.transactionResponse> transactions = transactionService.getTransaction(email);
        return ResponseEntity.ok(transactions);
    }
}
