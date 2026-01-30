package com.example.demo.transaction;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
