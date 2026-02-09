package com.example.demo.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

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

    private final RowMapper<Transaction> transactionRowMapper = (rs, rowNum) -> {
        Transaction transaction = new Transaction();
        transaction.setId(rs.getLong("id"));
        transaction.setUserid(rs.getLong("userid"));
        transaction.setAmount(rs.getLong("amount"));
        transaction.setTransaction_date(rs.getObject("transaction_date", OffsetDateTime.class));
        transaction.setCategory_id(rs.getLong("category_id"));
        transaction.setSource_id(rs.getLong("source_id"));
        transaction.setMemo(rs.getString("memo"));
        return transaction;
    };

    public List<Transaction> getTransactions(Long userId) {
        String SQL = "SELECT * FROM transaction WHERE userid = ?";

        List<Transaction> transactions = jdbcTemplate.query(SQL, transactionRowMapper, userId);
        return transactions;
    }


}