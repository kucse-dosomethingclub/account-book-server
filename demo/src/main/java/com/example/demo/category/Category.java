package com.example.demo.category;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Category {
    private Long id;
    private Long userid;
    private String name;
    private CategoryType type;
}