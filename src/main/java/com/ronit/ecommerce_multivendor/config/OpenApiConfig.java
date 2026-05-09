package com.ronit.ecommerce_multivendor.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().info(
                new Info().title("E-commerce Multivendor API")
                        .version("1.0")
                        .description("API documentation for E-commerce Multivendor application")
                        .contact(new Contact()
                                .name("Ronit Verma")
                                .email("ronitroy22678@gmail.com")
                                .url("https://ronit-verma-portfolio.vercel.app/"))

        );
    }
}
