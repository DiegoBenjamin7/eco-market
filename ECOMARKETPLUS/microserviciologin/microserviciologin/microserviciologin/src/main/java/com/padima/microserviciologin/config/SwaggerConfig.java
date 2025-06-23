package com.padima.microserviciologin.config;

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
            .title("Microservicio de Gestión de Sesiones - ECOMARKET")
            .version("v6.5")
            .description("Microservicio responsable de iniciar, consultar, actualizar y eliminar sesiones de usuario, incluyendo el registro de accesos.")
        );
    }

}
