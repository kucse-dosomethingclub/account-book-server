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
//아직 할일
//DB에 role만들기
//그리고 role를 어떻게 줄지 로직 짜기
//그리고 테스트 해보기
