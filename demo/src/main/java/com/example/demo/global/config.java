package com.example.demo.global;

import com.example.demo.jwt.JwtAuthenticationFilter;
import com.example.demo.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class config {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //jwt사용으로 기본 설정을 끔
                .httpBasic((HttpBasicConfigurer::disable))
                .csrf(csrf -> csrf.disable())

                //세션을 사용하지 않겠다
                .sessionManagement(session->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                //접근 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/api/login", "/api/signup").permitAll()
                        .anyRequest().authenticated()
                )
                //.authorizeHttpRequests(auth -> auth
                //    .requestMatchers("/admin/**").hasRole("ADMIN") // 'ADMIN' 권한만 들어와!
                //    .requestMatchers("/api/v1/**").hasRole("USER")  // 'USER' 권한만 들어와!
                //    .anyRequest().authenticated()
                //) => role를 사용해서 이렇게 가능

                //jWT를 서큐리티보다 먼저 가게만듬
                //즉 UsernamePasswordAuthenticationFiller이 실행되기 전에 JWT먼저 검사
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}