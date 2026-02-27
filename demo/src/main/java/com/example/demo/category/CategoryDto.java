package com.example.demo.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryDto {
    @Schema(description = "카테고리 요청 정보")
    public record categoryRequest(
            @Schema(description = "카테고리 이름", example = "월급")
            String name,
            @Schema(description = "카테고리 타입", example = "INCOME")
            CategoryType type
    ) {}

    @Schema(description = "카테고리 목록 요청 정보")
    public record categoryListRequest(
            @Schema(description = "유저 이메일")
            String email
    ){}

    @Schema(description = "카테고리 목록 응답 정보")
    public record categoryListResponse(
            @Schema(description = "카테고리 아이디", example = "1")
            Long id,
            @Schema(description = "카테고리 목록 이름", example = "월급")
            String name
    ){}
    //이메일를 받아서 카테고리 목록 보여주기
}
