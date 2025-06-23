package com.padima.microserviciocrearcuenta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
            new Info()
            .title("Microservicio de Gestión de Cuentas - ECOMARKET")
            .version("v6.5")
            .description("Microservicio para la creación, gestión y administración de cuentas de usuario")
        );
    }
}
