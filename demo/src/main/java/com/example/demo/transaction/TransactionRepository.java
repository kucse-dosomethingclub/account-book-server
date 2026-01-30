package com.example.demo.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TransactionRepository {

    private final JdbcTemplate jdbcTemplate;

    public void addTransaction(Transaction transaction) {
        String SQL = "INSERT INTO transaction (userid, amount, transaction_date, category_id, source_id, memo) VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(SQL,
                transaction.getUserid(),
                transaction.getAmount(),
                transaction.getTransaction_date(),
                transaction.getCategory_id(),
                transaction.getSource_id(),
                transaction.getMemo()
        );
    }


}