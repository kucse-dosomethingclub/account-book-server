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
        String SQL1 = "SELECT id FROM user_info WHERE email = ?";
        String SQL2 = "SELECT id, name FROM category WHERE userid = ?";
        int userid = jdbcTemplate.queryForObject(SQL1, Integer.class, email);
        //그냥 query로 하면 List로 반환이 된다, 하지만 queryForObject로 하고 뒤에 반환 타입을 적어주면 반환을 할 수 있다.
        return jdbcTemplate.query(SQL2, (rs, rowNum) -> new CategoryDto.categoryListResponse(
                rs.getLong("id"),
                rs.getString("name")
        ), userid);
    }
}
