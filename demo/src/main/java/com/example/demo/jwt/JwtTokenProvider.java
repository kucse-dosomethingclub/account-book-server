package com.example.demo.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {
    private final Key accessKey;
    private final Key refreshKey;
    private final long accessValidity;
    private final long refreshValidity;
    private final JdbcTemplate jdbcTemplate;


    //secret키를 가져와 암호화키로 변환을 함
    public JwtTokenProvider(@Value("${access.secret}") String accessSecretKey,//jwt.secret를 변수 secretKey에 넣기
                            @Value("${access.expiration.time}") long accessValidity,
                            @Value("${refresh.secret}") String refreshSecretKey,
                            @Value("${refresh.expiration.time}") long refreshValidity, JdbcTemplate jdbcTemplate){
        byte[] accessKeyBytes = accessSecretKey.getBytes(StandardCharsets.UTF_8);
        byte[] refreshKeyBytes = refreshSecretKey.getBytes(StandardCharsets.UTF_8);
        //secretKey를 바이트로 가져오기
        this.accessKey = Keys.hmacShaKeyFor(accessKeyBytes);
        this.refreshKey = Keys.hmacShaKeyFor(refreshKeyBytes);
        //바이트 배열을 암호화를 사용해서 키로 사용
        this.accessValidity = accessValidity;
        this.refreshValidity = refreshValidity;
        this.jdbcTemplate = jdbcTemplate;
    }

    public String createAccessToken(String email){
        Date now = new Date();
        Date validity = new Date(now.getTime()+accessValidity);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)//발행시간
                .setExpiration(validity)//만료시간
                .signWith(accessKey, SignatureAlgorithm.HS256)//키로 서명
                .compact();//모든 정보를 압축해서 하나의 문자열로 만듬
    }

    public String createRefreshToken(String email){
        Date now = new Date();
        Date validity = new Date(now.getTime()+refreshValidity);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)//발행시간
                .setExpiration(validity)//만료시간
                .signWith(refreshKey, SignatureAlgorithm.HS256)//키로 서명
                .compact();//모든 정보를 압축해서 하나의 문자열로 만듬
    }


    public int validationAccessToken(JwtDto.TokenResponse JwtToken){
        try {
            Jwts.parserBuilder().setSigningKey(accessKey).build().parseClaimsJws(JwtToken.accessToken());
            //parseClaimsJwt로 토큰을 검사 하는 감별기를 사용하는것
            //서명이 있는 토큰은 parseClaimsJws를 사용해야함
            return 1;
        } catch(ExpiredJwtException e) {
            log.info("access Token 만료");
            return 2;
        } catch(Exception e) {
            return 3;
        }
    }

    public String validationRefreshToken(String refreshToken){
        String SQL = "SELECT EXISTS(SELECT 1 FROM user_info WHERE refreshToken = ?)";
        String SQL1 = "SELECT email FROM user_info WHERE refreshToken = ?";
        try {
            if (jdbcTemplate.queryForObject(SQL, Boolean.class,refreshToken)) {
                log.info("검즘 SQL문 실행");
                String email = jdbcTemplate.queryForObject(SQL1, String.class, refreshToken);
                return createAccessToken(email);
            } else {
                log.info("SQL문 거짓");
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public Authentication getAuthentication(String token){
        String email = Jwts.parserBuilder().setSigningKey(accessKey).build()
                .parseClaimsJws(token).getBody().getSubject();
        //getBody => 내용물을 열고
        //getSubject => email을 가져와라
        return new UsernamePasswordAuthenticationToken(email,"",Collections.emptyList());
        //Collections.emptyList() => 수정 불가능한 객체가 반환된다 => 세번째 인자가 null이면 권한이 false로 들어가서
        // 저렇게 빈 객체라도 넣어줘야 한다.
        //스프링 서큐리티가 인증된 사람이라고 인식하는 양식 =>
        // 첫번째는 주체 즉, 유저의 이메일이나 아이디,
        // 두번째는 비밀번호인데 JWT로 인증을 해서 ""을 사용,
        //세번째는 권한.
    }
}
