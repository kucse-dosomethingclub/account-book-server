package com.example.demo.domain;

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
    private String type;
}