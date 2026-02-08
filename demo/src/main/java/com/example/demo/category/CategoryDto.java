package com.example.demo.category;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryDto {
    public record categoryRequest(
            String name,
            CategoryType type
    ) {}
}
