package com.example.demo.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(
            summary = "카테고리 목록 추가 엔드포인트",
            description = "카테고리 목록을 추가합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "카테고리 추가 성공"
            )
    })
    @PostMapping
    public ResponseEntity<String> newCategory(@RequestBody CategoryDto.categoryRequest requestDto, @AuthenticationPrincipal String email) {
        categoryService.addCategory(requestDto, email);
        return ResponseEntity.status(HttpStatus.CREATED).body("카테고리가 추가되었습니다.");
    }

    @Operation(
            summary = "카테고리 목록 확인 엔드포인트",
            description = "카테고리 목록을 확입합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "카테고리 목록 확인 완료"
            )
    })
    @GetMapping
    public ResponseEntity<List<CategoryDto.categoryListResponse>> getCategory(@RequestParam CategoryDto.categoryListRequest ListRequestDto){
        List<CategoryDto.categoryListResponse> categoryList = categoryService.getCategoryList(ListRequestDto.email());
        return ResponseEntity.ok(categoryList);
    }


}
