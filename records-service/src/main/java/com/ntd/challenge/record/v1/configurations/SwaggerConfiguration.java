package com.ntd.challenge.record.v1.configurations;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(name = "NTD_Software", scheme = "bearer", bearerFormat = "JWT", type = SecuritySchemeType.HTTP)
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openApiInfo() {
        return new OpenAPI().info(apiInfo());
    }

    @Bean
    public GroupedOpenApi apis() {
        return GroupedOpenApi.builder()
                .group("apis")
                .packagesToScan("com.ntd.challenge.record")
                .pathsToExclude("/internal/**")
                .build();
    }

    private Info apiInfo() {
        return new Info()
                .title("NTD Software")
                .description("NTD Software challenge - Auth Service APIs")
                .version("v1.0.0");
    }

}
