package com.scaler.bloggingapp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "*")
@OpenAPIDefinition(
        info = @Info(
                title = "Blogging App REST API",
                version = "1.0.0",
                description = "Allows to manage articles, users"
        )
)
@SecurityScheme(name="Credentials",scheme = "bearer", type = SecuritySchemeType.HTTP,bearerFormat = "Bearer", in = SecuritySchemeIn.HEADER)
public class BloggingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BloggingAppApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
