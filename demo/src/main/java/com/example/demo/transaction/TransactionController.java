package com.example.demo.transaction;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(
            summary = "거래내역 추가",
            description = "거래내역을 추가합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "거래내역 추가 성공")
    })
    public ResponseEntity<String> newTransaction(@RequestBody TransactionDto.transactionRequest requestDto, @AuthenticationPrincipal String email) {
        transactionService.saveTransaction(requestDto, email);
        return ResponseEntity.status(HttpStatus.CREATED).body("가계부 내역이 저장되었습니다.");
    }

    @GetMapping
    @Operation(
            summary = "전체 거래내역 확인",
            description = "전체 거래내역을 확인합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "전체 거래내역 불러오기 성공")
    })
    public ResponseEntity<List<TransactionDto.transactionResponse>> getTransactions(@AuthenticationPrincipal String email) {
        List<TransactionDto.transactionResponse> transactions = transactionService.getTransaction(email);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/GetCategory")
    @Operation(
            summary = "카테고리별 거래내역 확인",
            description = "카테고리별 거래내역을 확인합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "카테고리별 거래내역 불러오기 성공"
            )
    })
    public ResponseEntity<List<TransactionDto.transactionResponse>> getCategoryTrans(@RequestParam Long CategoryId, @AuthenticationPrincipal String email) {
        List<TransactionDto.transactionResponse> transactions = transactionService.getCategoryTrans(CategoryId, email);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("Get")




}
