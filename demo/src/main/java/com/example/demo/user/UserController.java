package com.example.demo.user;

import com.example.demo.jwt.JwtDto;
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
    public ResponseEntity<?> signup(@RequestBody User user) {
        try{
            User sigunupUser=userService.signUp(user);
            return ResponseEntity.ok(user);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto.LoginRequest request) {
        try {
            JwtDto.TokenResponse token = userService.login(request);
            UserDto.LoginRequest ResponseDto = new UserDto.LoginRequest(request.email(), request.password(), token.accessToken(), token.refreshToken());
            return ResponseEntity.ok(ResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/tokenExpired")
    public ResponseEntity<?> tokenExpired(HttpServletRequest request){
        String refreshToken = request.getHeader("Authorization_refresh");
        try{
            JwtDto.TokenResponse newton = userService.newToken(refreshToken);
            UserDto.accessToken ResponseDto = new UserDto.accessToken(newton.accessToken());
            return ResponseEntity.ok(ResponseDto);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @PostMapping("/password_change")
    public ResponseEntity<?> password_change(@RequestBody UserDto.LoginRequest request){
        try{
            User user=userService.psaaword_change(request);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //delete반환을 어떻게 할지....
    @PostMapping("/delete_user")
    public ResponseEntity<?> delete_user(@RequestBody UserDto.LoginRequest request){
        try{
            java.lang.Boolean delete =userService.delete_user(request);
            if (delete) {
                return ResponseEntity.ok(delete);
            }
            return ResponseEntity.ok(delete);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
