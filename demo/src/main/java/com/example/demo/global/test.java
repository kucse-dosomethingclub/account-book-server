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


//로그아웃에 대비해서 black list token을 만들어서 막기


