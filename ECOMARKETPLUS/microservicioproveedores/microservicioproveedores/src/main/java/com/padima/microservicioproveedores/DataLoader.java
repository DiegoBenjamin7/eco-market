package com.padima.microservicioproveedores;
import com.padima.microservicioproveedores.model.proveedores;
import com.padima.microservicioproveedores.repository.ProveedoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Random;

@Component
public class DataLoader implements CommandLineRunner {
    
    // Datos para generar proveedores aleatorios
    private static final List<String> NOMBRES_PROVEEDORES = List.of(
        "Importadora", "Distribuidora", "Comercial", "Suministros", 
        "Tecnologías", "Alimentos", "Materiales", "Servicios"
    );
    
    // Tipos de proveedores (tecnología, comestibles, etc.)
    private static final List<String> TIPOS_PROVEEDORES = List.of(
        "Tecnología", "Comestibles", "Construcción", "Logística",
        "Manufactura", "Servicios", "Retail", "Salud"
    );
    
    @Autowired
    private ProveedoresRepository proveedoresRepository;

    @Override
    public void run(String... args) {
        crearProveedoresDeEjemplo();
    }

    private void crearProveedoresDeEjemplo() {
        if (proveedoresRepository.count() == 0) {
            Random random = new Random();
            
            for (int i = 1; i <= 10; i++) {
                proveedores proveedor = new proveedores();
                
                // Generar datos aleatorios
                String nombreProveedor = generarNombreProveedor(random);
                String rut = generarRutSinGuion(random);
                String tipo = TIPOS_PROVEEDORES.get(random.nextInt(TIPOS_PROVEEDORES.size()));
                
                proveedor.setNombre(nombreProveedor);
                proveedor.setDireccion(generarDireccion(random));
                proveedor.setEmail(generarEmail(nombreProveedor));
                proveedor.setTelefono(generarTelefono(random));
                proveedor.setTipo(tipo); // Usamos solo tipo para la categoría
                proveedor.setRutEmpresa(rut);
                
                proveedoresRepository.save(proveedor);
                System.out.println("Proveedor creado: " + proveedor.getNombre() + 
                                 " - Tipo: " + tipo + 
                                 " - RUT: " + rut);
            }
        }
    }

    // Métodos auxiliares (igual que antes)...
    private String generarNombreProveedor(Random random) {
        String nombreBase = NOMBRES_PROVEEDORES.get(random.nextInt(NOMBRES_PROVEEDORES.size()));
        return nombreBase + " " + TIPOS_PROVEEDORES.get(random.nextInt(TIPOS_PROVEEDORES.size())) + 
               " " + (random.nextInt(50) + 1);
    }
    
    private String generarDireccion(Random random) {
        String[] calles = {"Av. Principal", "Calle Secundaria", "Pasaje Privado", "Camino Rural"};
        return calles[random.nextInt(calles.length)] + " " + (random.nextInt(2000) + 1);
    }
    
    private String generarEmail(String nombreProveedor) {
        return nombreProveedor.toLowerCase(Locale.ROOT)
                .replace(" ", ".")
                .replace("á", "a")
                .replace("é", "e")
                .replace("í", "i")
                .replace("ó", "o")
                .replace("ú", "u") + "@empresa.com";
    }
    
    private String generarTelefono(Random random) {
    // Formato: 91234567891 (11 dígitos)
    return String.valueOf(random.nextLong(90000000000L) + 10000000000L);
}

   private String generarRutSinGuion(Random random) {
    // Genera RUTs de 8 caracteres (7 dígitos + DV)
    int num = random.nextInt(9000000) + 1000000; // 7 dígitos
    char dv = calcularDigitoVerificador(num);
    return String.format("%d%c", num, dv); // Ejemplo: "1234567K"
}
    
    private char calcularDigitoVerificador(int rut) {
        int m = 0, s = 1;
        for (; rut != 0; rut /= 10) {
            s = (s + rut % 10 * (9 - m++ % 6)) % 11;
        }
        return (char) (s != 0 ? s + 47 : 75);
    }
}