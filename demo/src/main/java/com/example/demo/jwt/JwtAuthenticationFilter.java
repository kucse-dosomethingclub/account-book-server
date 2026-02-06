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
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws ServletException,IOException{
        //HTTP에서 토큰 가져오기
        JwtDto.TokenResponse JwtToken = resolveToken(request);

        if(JwtToken != null && jwtTokenProvider.validationAccessToken(JwtToken)){
            Authentication authentication = jwtTokenProvider.getAuthentication(JwtToken.accessToken());
            //토큰 유효성 검사
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //스프링 서큐리티에 접속자 저장
        }
        else{
            //access 재발급 로직
            System.out.println("재발급 로직 들어옴");
            String newAccessToken = jwtTokenProvider.validationRefreshToken(JwtToken != null ? JwtToken.refreshToken() : null);
            if(newAccessToken != null){
                JwtDto.TokenResponse newJwtToken = new JwtDto.TokenResponse(newAccessToken, JwtToken.refreshToken());
                Authentication authentication1 = jwtTokenProvider.getAuthentication(newJwtToken.accessToken());
                //토큰 유효성 검사
                System.out.println("재발급 로직 들어옴2");
                SecurityContextHolder.getContext().setAuthentication(authentication1);
                //스프링 서큐리티에 접속자 저장
            }
        }
        filterChain.doFilter(request,response);
        //다음으로 넘어가라
    }

    private JwtDto.TokenResponse resolveToken(HttpServletRequest request){
        String accessToken = request.getHeader("Authorization");
        String refreshToken = request.getHeader("Authorization_refresh");
        //헤더에서 Authorization에 해당하는거 가져옴
        if(StringUtils.hasText(accessToken) && StringUtils.hasText(refreshToken) && accessToken.startsWith("Bearer ")){
            return new JwtDto.TokenResponse(accessToken.substring(7),refreshToken);
            //7부터 끝까지 가져와라
        }
        //일단 access는 시작을 Bearer 로 시작하게 했고 refresh는 그냥 놔둠
        return null;
    }
}

//즉, 순서를 보자면 jwtAuthenticationFilter를 먼저 거치고 => UsernamePasswordAuthenticationFilter => AuthorizationFilter
