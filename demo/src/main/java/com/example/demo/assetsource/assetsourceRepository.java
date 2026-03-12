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
    private static final String GET_ASSETSOURCE_BY_ID = "SELECT * FROM assetsource WHERE id = ?";

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

    // 자금ID로 자금출처 조회
    public assetsource getAssetsourceById(Long assetsourceId) {
        return jdbcTemplate.queryForObject(GET_ASSETSOURCE_BY_ID, (rs, rowNum) -> {
            assetsource assetsource = new assetsource();
            assetsource.setId(rs.getLong("id"));
            assetsource.setUserid(rs.getLong("userid"));
            assetsource.setName(rs.getString("name"));
            return assetsource;
        }, assetsourceId);
    }

    // 본인의 자금출처가 맞는지 확인
    public boolean isAssetsourceOwner(Long assetsourceId, Long userId) {
        return getAssetsourceById(assetsourceId).getUserid().equals(userId);
    }
}
