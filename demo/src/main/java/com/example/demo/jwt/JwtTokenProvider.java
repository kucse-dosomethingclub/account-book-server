package com.example.demo.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final Key key;
    private final long validityInMilliseconds;

    //secret키를 가져와 암호화키로 변환을 함
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey,//jwt.secret를 변수 secretKey에 넣기
                            @Value("${jwt.expiration-time}") long validityInMilliseconds){
        byte[] keyBytes = Decoders.BASE64.decode(Base64.getEncoder().encodeToString(secretKey.getBytes()));
        //secretKey를 바이트로 가져와서 Base64라는 표준 형식의 문자열로 만들고 다시 바이트로 만든다.
        this.key= Keys.hmacShaKeyFor(keyBytes);
        //바이트 배열을 암호화를 사용해서 키로 사용
        this.validityInMilliseconds=validityInMilliseconds;
    }
    public String createToken(String email,String role){
        //JWT payload에 들어갈 내용
        Claims claims = Jwts.claims().setSubject(email);
        //추가로 넣을 내용
        claims.put("role",role);

        Date now = new Date();
        Date validity = new Date(now.getTime()+validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)//발행시간
                .setExpiration(validity)//만료시간
                .signWith(key, SignatureAlgorithm.HS256)//키로 서명
                .compact();//모든 정보를 압축해서 하나의 문자열로 만듬
    }

    public boolean validationToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            //parseClaimsJwt로 토큰을 검사 하는 감별기를 사용하는것
            //서명이 있는 토큰은 parseClaimsJws를 사용해야함
            return true;
        }catch(Exception e) {
            System.out.println("왜 여기에 있어");
            return false;
        }
    }

    public Authentication getAuthentication(String token){
        String email = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
        //getBody => 내용물을 열고
        //getSubject => email을 가져와라

        return new UsernamePasswordAuthenticationToken(email,"",null);
        //스프링 서큐리티가 인증된 사람이라고 인식하는 양식 =>
        // 첫번째는 주체 즉, 유저의 이메일이나 아이디,
        // 두번째는 비밀번호인데 JWT로 인증을 해서 ""을 사용,
        //세번째는 권한, 즉 role에 뭘 줄지...
    }
}
