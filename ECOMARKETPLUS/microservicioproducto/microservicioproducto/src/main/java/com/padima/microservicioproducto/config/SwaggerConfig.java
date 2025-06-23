package com.padima.microservicioproducto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI().info(
            new Info()
            .title("Microservicio de Gestión de Productos - ECOMARKET")
            .version("v6.5")
            .description("Microservicio encargado de registrar, consultar, actualizar y eliminar productos, incluyendo atributos como nombre, categoría, precio, stock y fecha de caducidad.")
        );
    }

}
