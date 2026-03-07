package com.example.demo.transaction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;

@Getter
@NoArgsConstructor
public class TransactionDto {
    @Schema(description = "거래내역 요청 정보")
    public record transactionRequest(
            @Schema(description = "가격", example = "20000")
            Long amount,
            @Schema(description = "거래 날짜(OffsetDateTime 형식)", example = "2026-03-04T17:30:00+09:00")
            String transactionDate,
            @Schema(description = "카테고리 아이디", example = "1")
            Long categoryId,
            @Schema(description = "자금출처 아이디", example = "2")
            Long sourceId,
            @Schema(description = "메모", example = "메모")
            String memo
    ) {}

    @Schema(description = "거래내역 응답정보")
    public record transactionResponse(
            @Schema(description = "거래 아이디", example = "1")
            Long id,
            @Schema(description = "거래 금액", example = "20000")
            Long amount,
            @Schema(description = "거래 날짜(OffsetDateTime 형식)", example = "2026-03-04T17:30:00+09:00")
            OffsetDateTime transactionDate,
            @Schema(description = "카테고리 아이디", example = "1")
            Long categoryId,
            @Schema(description = "자금출처 아이디", example = "1")
            Long sourceId,
            @Schema(description = "메모", example = "메모")
            String memo
    ) {}

    public record CategoryTransRequest(
            Long categoryId
    ) {}



}
