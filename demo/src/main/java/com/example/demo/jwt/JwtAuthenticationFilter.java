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
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    //매개변수
    //첫번째는 사용자의 요청 받는거
    //두번째는 우리의 응답
    //세번째는 순서 제어 즉, 다음 필터로 유저 보내기
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        //HTTP에서 토큰 가져오기
        JwtDto.TokenResponse JwtToken = resolveToken(request);
        String path = request.getRequestURI();
        //URI는 순수 경로만 뽑아서 준다. 즉, /user/login 처럼 이것만 뽑아서 path에 준다.
        if (path.startsWith("/api/tokenExpired")) {
            filterChain.doFilter(request, response);
            return;
        }
        //tokenExpired일때는 access 검증 뛰어넘기
        if (JwtToken != null && jwtTokenProvider.validationAccessToken(JwtToken) == 1) {
            log.info("access token 인증 완료");
            Authentication authentication = jwtTokenProvider.getAuthentication(JwtToken.accessToken());
            //토큰 유효성 검사
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //스프링 서큐리티에 접속자 저장
        } else if (jwtTokenProvider.validationAccessToken(JwtToken)==2) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charest=UTF-8");
            String errormessage = "{\"status\": 600, \"message\":\"access token expired\"}";
            response.getWriter().write(errormessage);
            return;
        }
        filterChain.doFilter(request,response);
        //다음으로 넘어가라
    }

    private JwtDto.TokenResponse resolveToken(HttpServletRequest request){
        String accessToken = request.getHeader("Authorization");
        String refreshToken = request.getHeader("Authorization_refresh");
        //헤더에서 Authorization에 해당하는거 가져옴
        if (StringUtils.hasText(accessToken) && StringUtils.hasText(refreshToken) && accessToken.startsWith("Bearer ")) {
            return new JwtDto.TokenResponse(accessToken.substring(7),refreshToken);
            //7부터 끝까지 가져와라
        }
        //일단 access는 시작을 Bearer 로 시작하게 했고 refresh는 그냥 놔둠
        return null;
    }
}

//즉, 순서를 보자면 jwtAuthenticationFilter를 먼저 거치고 => UsernamePasswordAuthenticationFilter => AuthorizationFilter
