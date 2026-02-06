package com.example.demo.user;

import com.example.demo.jwt.JwtDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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

    public void login(String email, String refreshToken){
        String SQL = "UPDATE user_info SET refreshtoken = ? WHERE email = ?";
        try{
            jdbcTemplate.update(SQL, refreshToken, email);
        }catch (Exception e){
            return;
        }

    }

    public Optional<User> password_ch(User user,String password){
        String SQL = "UPDATE user_info SET password = ? WHERE email = ?";
        try{
            int result = jdbcTemplate.update(SQL,password,user.getEmail());
            if(result>0){
                return Optional.of(user);
            }
            return Optional.empty();
        }catch (Exception e){
            return Optional.empty();
        }
    }

    public Boolean delete_U(User user){
        String SQL = "DELETE FROM user_info WHERE email=?";
        try{
            int result = jdbcTemplate.update(SQL,user.getEmail());
            if(result>0){
                return true;
            }
            return false;
        }catch(Exception e){
            return false;
        }
    }

    //true반환 => 중복 false => 중복되지 않음
    public Optional<User> email_check(User user){
        String SQL = "SELECT EXISTS(SELECT 1 FROM user_info WHERE email=?)";
        try{
            if(!jdbcTemplate.queryForObject(SQL,Boolean.class,user.getEmail())){
                return Optional.of(user);
            }
            else{
                return Optional.empty();
            }

        }catch(Exception e){
            //예외처리가 될 수 있으니 콘솔로 찍어서 확인하기
            return Optional.empty();
        }
    }

    public Optional<User> findByEmail(String email) {
        String SQL = "SELECT * FROM user_info WHERE email=?";

        try {
            User foundUser = jdbcTemplate.queryForObject(SQL, (rs, rowNum) -> User.builder()
                    .id(rs.getLong("id"))
                    .email(rs.getString("email"))
                    .username(rs.getString("username"))
                    .password(rs.getString("password"))
                    .build(),
                    email
            );
            //rs = DB에서 가져온 데이터
            //rowNum = 현재 몇번째줄을 처리하고 있는지
            return Optional.ofNullable(foundUser);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
