package com.example.demo.assetsource;

import com.example.demo.category.CategoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class assetsourceRepository {

    private final JdbcTemplate jdbcTemplate;

    public void addAssetsource(assetsource assetsource) {
        String SQL = "INSERT INTO assetsource (userid, name) VALUES (?, ?)";

        jdbcTemplate.update(SQL,
                assetsource.getUserid(),
                assetsource.getName());
    }

    public List<assetsourceDto.assetsourceResponse> getAssetsource(Long userId) {
        String SQL = "SELECT id, name FROM assetsource WHERE userid = ?";

        return jdbcTemplate.query(SQL, (rs, rowNum) -> new assetsourceDto.assetsourceResponse(
                rs.getLong("id"),
                rs.getString("name")

        ), userId);
    }
}
