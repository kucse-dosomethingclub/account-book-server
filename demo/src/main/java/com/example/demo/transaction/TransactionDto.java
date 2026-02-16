package com.example.demo.transaction;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Getter
@NoArgsConstructor
public class TransactionDto {
    public record transactionRequest(
            Long amount,
            String transactionDate,
            Long categoryId,
            Long sourceId,
            String memo
    ) {}

    public record transactionResponse(
            Long id,
            Long amount,
            OffsetDateTime transactionDate,
            Long categoryId,
            Long sourceId,
            String memo
    ) {}
}
