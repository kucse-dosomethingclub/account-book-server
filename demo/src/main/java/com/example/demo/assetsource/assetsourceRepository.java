package com.example.demo.assetsource;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class assetsourceRepository {

    private final JdbcTemplate jdbcTemplate;

    public void addAssetsource(assetsource assetsource) {
        String SQL = "INSERT INTO assetsource (userid, name, type) VALUES (?, ?, ?)";

        jdbcTemplate.update(SQL,
                assetsource.getUserid(),
                assetsource.getName(),
                assetsource.getType().name());
    }
}
