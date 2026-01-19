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
    //true반환 => 중복 false => 중복되지 않음
    public boolean email_check(User user){
        try{
            String SQL = "SELECT EXISTS(SELECT 1 FROM user_info WHERE email=?)";
            return jdbcTemplate.queryForObject(SQL,Boolean.class,user.getEmail());
        }catch(Exception e){
            //예외처리가 될 수 있으니 콘솔로 찍어서 확인하기
            return false;
        }
    }
}
