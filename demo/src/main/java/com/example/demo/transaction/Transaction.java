package com.example.demo.transaction;

import java.time.OffsetDateTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Transaction {
    private Long id;
    private Long userid;
    private Long amount;
    private OffsetDateTime transaction_date;
    private Long category_id;
    private Long source_id;
    private String memo;
}
