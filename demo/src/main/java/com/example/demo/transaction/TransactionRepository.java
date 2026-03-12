package com.example.demo.transaction;

import com.example.demo.category.CategoryType;
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
        return Transaction.builder()
                .id(rs.getLong("id"))
                .amount(rs.getLong("amount"))
                .transaction_date(rs.getObject("transaction_date", OffsetDateTime.class))
                .category_id(rs.getLong("category_id"))
                .source_id(rs.getLong("source_id"))
                .memo(rs.getString("memo"))
                .build();
    };

    public List<Transaction> getTransactions(Long userId) {
        String SQL = "SELECT id, amount, transaction_date, category_id, source_id, memo FROM transaction WHERE userid = ?";

        return jdbcTemplate.query(SQL, transactionRowMapper, userId);
    }

    public List<Transaction> getTransactions(Long categoryId, Long userId) {
        String getAllTransSQL = "SELECT id, amount, transaction_date, category_id, source_id, memo FROM transaction WHERE userid = ?";
        String getCategoryTransSQL = "SELECT id, amount, transaction_date, category_id, source_id, memo FROM transaction WHERE userid = ? AND category_id = ?";
        if(categoryId == 0){
            return jdbcTemplate.query(getAllTransSQL, transactionRowMapper, userId);
        }
        else{
            return jdbcTemplate.query(getCategoryTransSQL, transactionRowMapper, userId, categoryId);
        }
    }

    public List<Transaction> getIncomeAmountTrans(Long userid) {
        String SQL = "SELECT id, amount, transaction_date, category_id, source_id, memo FROM transaction WHERE userid = ? AND amount > 0";

        return jdbcTemplate.query(SQL, transactionRowMapper, userid);
    }

    public List<Transaction> getExpenseAmountTrans(Long userid) {
        String SQL = "SELECT id, amount, transaction_date, category_id, source_id, memo FROM transaction WHERE userid = ? AND amount < 0";

        return jdbcTemplate.query(SQL, transactionRowMapper, userid);
    }

    public List<Transaction> getByAssetId(Long assetId, Long userid) {
        String getByAssetIdSQL = "SELECT id, amount, transaction_date, category_id, source_id, memo FROM transaction WHERE userid = ? AND source_id = ?";
        return jdbcTemplate.query(getByAssetIdSQL, transactionRowMapper, userid, assetId);
    }
}