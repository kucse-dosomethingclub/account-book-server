package com.example.demo.category;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public void addCategory(Category category) {
        String SQL = "INSERT INTO category (userid, name, type) VALUES (?, ?, ?)";

        jdbcTemplate.update(SQL,
                category.getUserid(),
                category.getName(),
                category.getType().name()
        );
    }
}
