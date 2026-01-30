package com.example.demo.transaction;

import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
