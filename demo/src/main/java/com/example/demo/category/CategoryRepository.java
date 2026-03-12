package com.example.demo.category;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final String GET_CATEGORY_BY_ID = "SELECT * FROM category WHERE id = ?";

    public void addCategory(Category category) {
        String SQL = "INSERT INTO category (userid, name, type) VALUES (?, ?, ?)";
        try{
            jdbcTemplate.update(SQL,
                    category.getUserid(),
                    category.getName(),
                    category.getType().name());
        }catch (Exception e){
            return;
        }
    }

    public List<CategoryDto.categoryListResponse> getCategory(String email){
        String SQL = "SELECT c.id, c.name FROM category c JOIN user_info u ON c.userid = u.id WHERE u.email = ?";
        return jdbcTemplate.query(SQL, (rs, rowNum) -> new CategoryDto.categoryListResponse(
                rs.getLong("id"),
                rs.getString("name")
        ), email);
    }

    // 카테고리ID로 카테고리를 조회
    public Category getCategoryById(Long categoryId) {
        return jdbcTemplate.queryForObject(GET_CATEGORY_BY_ID, (rs, rowNum) -> {
            Category category = new Category();
            category.setId(rs.getLong("id"));
            category.setUserid(rs.getLong("userid"));
            category.setName(rs.getString("name"));
            category.setType(CategoryType.valueOf(rs.getString("type")));
            return category;
        }, categoryId);
    }

    // 본인의 카테고리가 맞는지 확인
    public boolean isCategoryOwner(Long categoryId, Long userId) {
        return getCategoryById(categoryId).getUserid().equals(userId);
    }
}
