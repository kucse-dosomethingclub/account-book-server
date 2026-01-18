package com.example.demo.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class config {
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 가장 많이 사용되는 BCrypt 방식의 암호화 도구를 빈(Bean)으로 등록합니다.
        return new BCryptPasswordEncoder();
    }
}
