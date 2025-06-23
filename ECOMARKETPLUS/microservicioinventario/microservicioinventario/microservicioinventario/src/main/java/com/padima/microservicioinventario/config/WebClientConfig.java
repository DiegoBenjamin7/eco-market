package com.padima.microservicioinventario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .baseUrl("http://localhost:8080/api/perfulandia/usuarios")
                .defaultHeader("X-API-KEY", "1234sdf")
                .build();
    }

}
