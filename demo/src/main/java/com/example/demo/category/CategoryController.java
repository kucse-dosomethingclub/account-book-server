package com.example.demo.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<String> newCategory(@RequestBody CategoryDto.categoryRequest requestDto, @AuthenticationPrincipal String email) {
        categoryService.addCategory(requestDto, email);
        return ResponseEntity.status(HttpStatus.CREATED).body("카테고리가 추가되었습니다.");
    }

}
