package com.example.demo.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        if(userService.signUp(user)){
            return "회원가입 완료";
        }
        else{
            return "이메일 중복으로 회원가입 실패";
        }
    }

    @Data
    public static class LoginRequest {
        private String email;
        private String password;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            User loginUser = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(loginUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
