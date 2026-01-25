package com.example.demo.global;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(){
        Info info = new Info()
                .title("가계부 API")
                .version("1.0.0")
                .description("가계부 프로젝트 백엔드 API 명세서");
        return new OpenAPI()
                .info(info);
    }
}

