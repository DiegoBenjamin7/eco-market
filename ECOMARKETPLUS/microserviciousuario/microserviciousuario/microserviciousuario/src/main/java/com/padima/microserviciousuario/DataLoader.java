package com.padima.microserviciousuario;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.padima.microserviciousuario.model.Usuario;
import com.padima.microserviciousuario.services.UsuarioService;
import net.datafaker.Faker;

@Component
public class DataLoader implements CommandLineRunner {

    private final Faker faker = new Faker(new Locale("es", "cl"));

    @Autowired
    private UsuarioService usuarioService;  // Cambiado a minúscula

    @Override
    public void run(String... args) throws Exception {
        for(int i = 0; i < 30; i++) {
            Usuario usuarioFicticio = new Usuario();
            
            // Usando los campos reales de tu clase Usuario
            usuarioFicticio.setRut(generarRutFicticio());
            usuarioFicticio.setNombre(faker.name().fullName());
            usuarioFicticio.setMail(faker.internet().emailAddress());
            usuarioFicticio.setTelefono(Long.parseLong(faker.phoneNumber().cellPhone().replaceAll("\\D+", "")));
            usuarioFicticio.setDireccion(faker.address().fullAddress());

            usuarioService.GuardarUsuario(usuarioFicticio);  // Manteniendo el nombre original del método
            System.out.println("Usuario Registrado: " + usuarioFicticio.getRut());
        }
    }

    private Long generarRutFicticio() {
        // Genera un número de 8 dígitos para el RUT (sin dígito verificador)
        return faker.number().numberBetween(10_000_000L, 99_999_999L);
    }
}