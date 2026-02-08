package com.example.demo.global;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        String accessTokenScheme = "jwtAuth";
        String refreshTokenHeader = "Authorization_refresh";

        SecurityScheme securityScheme = new SecurityScheme()
                .name(accessTokenScheme)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        SecurityScheme refreshScheme = new SecurityScheme()
                .name(refreshTokenHeader)
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER);

        Info info = new Info()
                .title("가계부 API")
                .version("1.0.0")
                .description("가계부 프로젝트 백엔드 API 명세서");

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(accessTokenScheme)
                        .addList(refreshTokenHeader))
                .components(new Components()
                        .addSecuritySchemes(accessTokenScheme, securityScheme)
                        .addSecuritySchemes(refreshTokenHeader, refreshScheme))
                .info(info);
    }
}

