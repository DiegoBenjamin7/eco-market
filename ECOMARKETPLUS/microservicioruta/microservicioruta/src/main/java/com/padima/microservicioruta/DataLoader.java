package com.padima.microservicioruta;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.padima.microservicioruta.model.Ruta;
import com.padima.microservicioruta.service.RutaService;

@Component
public class DataLoader implements CommandLineRunner {
    
    // 1. Lista fija de ciudades para hacerlo más claro
    private static final List<String> CIUDADES = List.of(
        "Santiago", "Valparaíso", "Concepción", "La Serena", 
        "Antofagasta", "Iquique", "Puerto Montt", "Punta Arenas"
    );

    @Autowired
    private RutaService rutaService;

    @Override
    public void run(String... args) {
        crearRutasDeEjemplo();
    }

    private void crearRutasDeEjemplo() {
        for (int i = 1; i <= 5; i++) { // Creamos solo 5 rutaso
            Ruta ruta = new Ruta();
        
            ruta.setCodigoRuta("RUT-" + i); 
            ruta.setOrigen(obtenerCiudadAleatoria());
            ruta.setDestino(obtenerCiudadDiferente(ruta.getOrigen()));
            ruta.setDistanciaKm(100.0 * i); // Distancia 
            ruta.setTiempoEstimadoMinutos(60 * i); // Tiempo 
            ruta.setTipoTransporte(obtenerTransporteAleatorio());
            ruta.setEstado("ACTIVA");

            rutaService.crearRuta(ruta);
            System.out.println("Ruta creada: " + ruta.getCodigoRuta());
        }
    }

    private String obtenerCiudadAleatoria() {
        Random random = new Random();
        return CIUDADES.get(random.nextInt(CIUDADES.size()));
    }

    private String obtenerCiudadDiferente(String ciudadOrigen) {
        String ciudad;
        do {
            ciudad = obtenerCiudadAleatoria();
        } while (ciudad.equals(ciudadOrigen));
        return ciudad;
    }

    private String obtenerTransporteAleatorio() {
        String[] transportes = {"TERRESTRE", "AEREO", "MARITIMO"};
        Random random = new Random();
        return transportes[random.nextInt(transportes.length)];
    }
}
