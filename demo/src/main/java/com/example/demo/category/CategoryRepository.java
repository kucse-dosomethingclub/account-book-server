package com.example.demo.category;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<CategoryDto.categoryListResponse> getCategory(String email){
        String SQL = "SELECT c.id, c.name FROM category c JOIN user_info u ON c.userid = u.id WHERE u.email = ?";
        return jdbcTemplate.query(SQL, (rs, rowNum) -> new CategoryDto.categoryListResponse(
                rs.getLong("id"),
                rs.getString("name")
        ), email);
    }
}
