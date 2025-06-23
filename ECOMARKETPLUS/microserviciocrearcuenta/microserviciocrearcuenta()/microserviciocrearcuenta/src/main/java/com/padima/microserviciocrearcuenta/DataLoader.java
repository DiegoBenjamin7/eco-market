package com.padima.microserviciocrearcuenta;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.padima.microserviciocrearcuenta.model.Cuenta;
import com.padima.microserviciocrearcuenta.service.CuentaService;

@Component
public class DataLoader implements CommandLineRunner {
    
    private static final List<String> NOMBRES = List.of(
        "Diego Sepulveda", "María González", "Carlos López", "Ana Martínez",
        "Pedro Sánchez", "Lucía Ramírez", "Diego Fernández", "Sofía Díaz"
    );
    
    @Autowired
    private CuentaService cuentaService;

    @Override
    public void run(String... args) {
        crearCuentasDeEjemplo();
    }

    private void crearCuentasDeEjemplo() {
        for (int i = 1; i <= 10; i++) { // Creamos 10 cuentas de ejemplo
            Cuenta cuenta = new Cuenta();
            
            // Configuramos los datos básicos
            String username = generarUsername(i);
            cuenta.setUsername(username);
            cuenta.setEmail(generarEmail(username));
            cuenta.setPassword(generarPasswordSegura());
            cuenta.setNombreCompleto(obtenerNombreAleatorio());
            cuenta.setEstado(obtenerEstadoAleatorio());
            cuenta.setFechaCreacion(Instant.now());

            cuentaService.guardar(cuenta);
            System.out.println("La Cuenta fue creada: " + cuenta.getUsername());
        }
    }

    private String generarUsername(int index) {
        return "user" + index;
    }

    private String generarEmail(String username) {
        return username + "@duocuc.cl";
    }

    private String generarPasswordSegura() {
        return "P@ssw0rd" + new Random().nextInt(100);
    }

    private String obtenerNombreAleatorio() {
        Random random = new Random();
        return NOMBRES.get(random.nextInt(NOMBRES.size()));
    }

    private String obtenerEstadoAleatorio() {
        String[] estados = {"ESTA ACTIVA", "ESTA INACTIVA", "ESTA BLOQUEADA"};
        return estados[new Random().nextInt(estados.length)];
    }
}