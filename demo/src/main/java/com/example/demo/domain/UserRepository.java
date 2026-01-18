package com.example.demo.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public void saveUser(User user) {
        String SQL = "INSERT INTO user_info (email, username, password) VALUES (?, ?, ?)";

        jdbcTemplate.update(SQL,
                user.getEmail(),
                user.getUsername(),
                user.getPassword()
        );
    }
}
