package com.example.demo.global;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class test {
    @GetMapping("/test_source")
    public ResponseEntity<String> testfunction(){
        return ResponseEntity.ok("JWT 검증 성공");

    }
}


//그리고 role를 어떻게 줄지 로직 짜기 => 생각해봐야 할듯....

