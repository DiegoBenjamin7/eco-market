package com.padima.microservicionotificacion.config;

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
            .title("Microservicio de Gestión de Notificación - ECOMARKET")
            .version("v6.5")
            .description("Microservicio encargado de gestionar el envío de notificaciones a los usuarios del sistema de ventas de ECOMARKET. Permite generar y consultar notificaciones relacionadas con eventos como pagos, órdenes de compra y cambios de estado.")
        );
    }

}
