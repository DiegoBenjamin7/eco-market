package com.padima.microserviciopago.config;

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
            .title("Microservicio de Gestión de Pago - PERFULANDIA")
            .version("v5.6")
            .description("Microservicio encargado de procesar y consultar los pagos realizados por los usuarios, relacionados con las órdenes de compra del sistema de ventas.")
        );
    }

}
