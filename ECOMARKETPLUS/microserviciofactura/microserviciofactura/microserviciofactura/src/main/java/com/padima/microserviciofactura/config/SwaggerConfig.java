package com.padima.microserviciofactura.config;

// src/main/java/com/example/invoiceservice/config/SwaggerConfig.java


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservicio de Gestion de Facturas - ECOMARKET")
                        .version("6.5")
                        .description("API para el microservicio de facturación."));
    }
}
