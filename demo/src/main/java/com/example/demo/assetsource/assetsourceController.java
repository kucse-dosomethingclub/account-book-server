package com.example.demo.assetsource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assetsource")
@RequiredArgsConstructor
public class assetsourceController {

    private final assetsourceService assetsourceService;

    @PostMapping
    public ResponseEntity<String> newAssetSource(@RequestBody assetsourceDto.assetsourceRequest requestDto, @AuthenticationPrincipal String email) {
        assetsourceService.addAssetsource(requestDto, email);
        return ResponseEntity.status(HttpStatus.CREATED).body("자산 출처가 추가되었습니다.");
    }
}
