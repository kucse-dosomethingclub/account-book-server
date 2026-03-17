package com.example.demo.transaction;

import com.example.demo.category.CategoryType;
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
    @Operation(summary = "거래내역 추가", description = "거래내역을 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "거래내역 추가 성공"),
            @ApiResponse(responseCode = "403", description = "거래내역 추가 실패")
    })
    public ResponseEntity<String> newTransaction(@RequestBody TransactionDto.transactionRequest requestDto, @AuthenticationPrincipal String email) {
        try {
            transactionService.saveTransaction(requestDto, email);
            return ResponseEntity.status(HttpStatus.CREATED).body("가계부 내역이 저장되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping
    @Operation(
            summary = "거래내역 확인",
            description = "카테고리 Id에 0이 들어오면 유저의 전체 거래내역을 확인하고," +
                    "Id에 숫자가 들어오면 그 카테고리에 대한 거래내역을 확인합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "거래내역 불러오기 성공")
    })
    public ResponseEntity<List<TransactionDto.transactionResponse>> getTransactions(@RequestParam Long categoryId, @AuthenticationPrincipal String email) {
        List<TransactionDto.transactionResponse> transactions = transactionService.getTransactions(categoryId, email);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/Amount")
    @Operation(
            summary = "수익과 지출 필터",
            description = "수익과 지출을 나누어 거래 내역을 얻어옵니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "수익, 지출 거래내역 불러오기 성공"
            )
    })
    public ResponseEntity<List<TransactionDto.transactionResponse>> getAmountTrans(@RequestParam CategoryType categoryType, @AuthenticationPrincipal String email){
        List<TransactionDto.transactionResponse> amountTrans = transactionService.getAmountTrans(categoryType, email);
        return ResponseEntity.ok(amountTrans);
    }

    @GetMapping("/byAssetId")
    @Operation(
            summary = "자산 ID로 내역 조회",
            description = "개인 자산 ID를 기준으로 거래 내역을 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "거래내역 조회 성공"
            )
    })
    public ResponseEntity<List<TransactionDto.transactionResponse>> getByAssetId(@RequestParam Long assetId,
                                                                                 @AuthenticationPrincipal String email) {
        List<TransactionDto.transactionResponse> assetTrans = transactionService.getByAssetId(assetId, email);
        return ResponseEntity.ok(assetTrans);
    }
}
