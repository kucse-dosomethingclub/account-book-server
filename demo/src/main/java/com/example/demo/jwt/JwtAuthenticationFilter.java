package com.example.demo.jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    //매개변수
    //첫번째는 사용자의 요청 받는거
    //두번째는 우리의 응답
    //세번째는 순서 제어 즉, 다음 필터로 유저 보내기
    protected  void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws ServletException,IOException{
        String token = resolveToken(request);
        //HTTP에서 토큰 가져오기

        if(token!=null&& jwtTokenProvider.validationToken(token)){
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            //토큰 유효성 검사
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //스프링 서큐리티에 접속자 저장
        }

        filterChain.doFilter(request,response);
        //다음으로 넘어가라
    }

    private  String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        //헤더에서 Authorization에 해당하는거 가져옴
        if(StringUtils.hasText(bearerToken)&& bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
            //7부터 끝까지 가져와라
        }
        return null;
    }
}

//즉, 순서를 보자면 jwtAuthenticationFilter를 먼저 거치고 => UsernamePasswordAuthenticationFilter => AuthorizationFilter
