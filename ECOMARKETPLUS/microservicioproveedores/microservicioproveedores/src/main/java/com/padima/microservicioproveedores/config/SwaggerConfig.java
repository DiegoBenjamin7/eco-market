package com.padima.microservicioproveedores.config;

import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI().info(
            new Info()
            .title("Microservicio de Gestión de los proveedores de EcoMarket")
            .version("v6.5")
            .description("Microservicio responsable de gestionar las proveedores."));
    }

}
