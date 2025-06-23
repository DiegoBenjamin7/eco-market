package com.padima.microservicioenvio;

import com.padima.microservicioenvio.model.Envio;
import com.padima.microservicioenvio.service.EnvioService;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class DataLoader implements CommandLineRunner {
    
    private final EnvioService envioService;
    private final Faker faker = new Faker(new Locale("es"));
    
    public DataLoader(EnvioService envioService) {
        this.envioService = envioService;
    }

    @Override
    public void run(String... args) {
        crearEnviosDeEjemplo();
    }

    private void crearEnviosDeEjemplo() {
        // Creamos 10 envíos de ejemplo
        for (int i = 1; i <= 10; i++) {
            Envio envio = new Envio();
            
            // Configuramos los datos básicos
            envio.setCodigoEnvio("ENV-" + i + "-" + faker.regexify("[A-Z0-9]{6}"));
            envio.setIdRuta(faker.number().numberBetween(1L, 100L)); // ID de ruta aleatorio
            envio.setIdPedido(faker.number().numberBetween(1000L, 9999L)); // ID de pedido aleatorio
            envio.setEstado(obtenerEstadoAleatorio());
            envio.setPesoKg(faker.number().randomDouble(2, 1, 50)); // Peso entre 1 y 50 kg
            envio.setDireccionDestino(faker.address().fullAddress());
            
            envioService.crearEnvio(envio);
            System.out.println("Envío creado: " + envio.getCodigoEnvio());
        }
    }

    private String obtenerEstadoAleatorio() {
        String[] estados = {"PREPARACION", "EN_TRANSITO", "ENTREGADO", "CANCELADO"};
        return estados[faker.random().nextInt(estados.length)];
    }
}
