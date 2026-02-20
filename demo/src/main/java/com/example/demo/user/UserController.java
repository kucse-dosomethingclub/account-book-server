package com.example.demo.user;

import com.example.demo.jwt.JwtDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    @Operation(
            summary = "회원가입 엔드포인트",
            description = "이메일과 비밀번호로 회원가입을 진행합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "회원가입 성공")
    })
    public ResponseEntity<?> signup(@RequestBody User user) {
        try{
            User sigunupUser = userService.signUp(user);
            return ResponseEntity.ok(user);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    @Operation(
            summary = "로그인 엔드포인트",
            description = "이메일과 비밀번호로 로그인을 진행합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.LoginResponse.class))
                    )
    })
    public ResponseEntity<?> login(@RequestBody() UserDto.LoginRequest request) {
        try {
            JwtDto.TokenResponse token = userService.login(request);
            UserDto.LoginResponse ResponseDto = new UserDto.LoginResponse(token.accessToken(), token.refreshToken());
            return ResponseEntity.ok(ResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/tokenExpired")
    @Operation(
            summary = "access 재발급 엔드포인트",
            description = "access token과 refresh token을 받아서 refresh token을 사용해서 access token 재발급을 진행합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "access token 재발급 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.accessTokenResponse.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "access token 재발급 실패"
            )
    })
    public ResponseEntity<?> tokenExpired(HttpServletRequest request){
        String refreshToken = request.getHeader("Authorization_refresh");
        try{
            JwtDto.TokenResponse newtoken = userService.newToken(refreshToken);
            UserDto.accessTokenResponse ResponseDto = new UserDto.accessTokenResponse(newtoken.accessToken());
            return ResponseEntity.ok(ResponseDto);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/password_change")
    @Operation(
            summary = "비밀번호 바꾸는 엔드포인트",
            description = "이메일과 비밀번호를 검증하고 비밀번호 변경을 진행합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공")
    })
    public ResponseEntity<?> password_change(@RequestBody UserDto.LoginRequest request){
        try{
            User user = userService.psaaword_change(request);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //delete반환을 어떻게 할지....
    @PostMapping("/delete_user")
    @Operation(
            summary = "유저 삭제 엔드포인트",
            description = "이메일과 비밀번호를 검증하고 유저 삭제를 진행합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 삭제 성공")
    })
    public ResponseEntity<?> delete_user(@RequestBody UserDto.LoginRequest request){
        try{
            java.lang.Boolean delete = userService.delete_user(request);
            if (delete) {
                return ResponseEntity.ok(delete);
            }
            return ResponseEntity.ok(delete);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
