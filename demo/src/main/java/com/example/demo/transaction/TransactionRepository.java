/*package com.example.demo.domain;

import org.springframework.jdbc.core.JdbcTemplate; // JdbcTemplate 사용을 위해 필수
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import java.time.OffsetDateTime; // transaction_data 처리를 위해 필수
import org.springframework.jdbc.core.RowMapper; // 조회 기능 사용 시 필요


@Repository
@RequiredArgsConstructor
public class TransactionRepository {
    private final JdbcTemplate jdbcTemplate;

    public void save(Transaction transaction){
        String sql="INSERT INTO transaction (userid,amount,transaction_data,category_id,source_id,memo)"+
                "VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                transaction.getUserid(),
                transaction.getAmount(),
                transaction.getTransaction_data(),
                transaction.getCategory_id(),
                transaction.getSource_id(),
                transaction.getMemo()
                );
    }
}*/
