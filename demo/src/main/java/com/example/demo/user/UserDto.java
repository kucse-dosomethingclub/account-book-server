package com.example.demo.user;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserDto {
    @Schema(description = "로그인 요청 정보")
    public record LoginRequest(
            @Schema(description = "이메일", example = "100test@google.com")
            String email,
            @Schema(description = "비밀번호", example = "googlepassword")
            String password
    ){}

    @Schema(description = "로그인 응답 정보")
    public record LoginResponse(
            @Schema(description = "access 토큰")
            String accessToken,
            @Schema(description = "refresh 토큰")
            String refreshToken
    ){}

    //public record password_ch(String email, String newpassword){}
    //record를 사용하면
    //1. 필드를 생성해줌 ex) private final String email처럼 만들어야 하지만 이걸 자동으로 만들어줌
    //2. 생성자 만듬
    //3. Getter 매서드 만들어줌

    @Schema(description = "토큰 재발급 응답 정보")
    public record accessTokenResponse(
            @Schema(description = "재발급된 access 토큰")
            String accessToken
    ){}

//    @Schema(description = "일반 엔드포인트 접근 형식")
//    public record normalRequest(
//            @Schema(description = "access 토큰")
//            String accessToken,
//
//            @Schema(description = "refresh 토큰")
//            String refreshToken
//    ){}
}
