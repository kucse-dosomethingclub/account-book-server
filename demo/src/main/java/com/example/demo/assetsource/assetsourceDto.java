package com.example.demo.assetsource;

import com.example.demo.category.CategoryType;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class assetsourceDto {
    public record assetsourceRequest(
            String name
    ) {}

    public record assetsourceResponse(
            Long id,
            String name
    ) {}
}
