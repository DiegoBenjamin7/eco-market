package com.padima.microservicioproducto;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.padima.microservicioproducto.model.Producto;
import com.padima.microservicioproducto.service.ProductoService;

import net.datafaker.Faker;

@Component
public class DataLoader implements CommandLineRunner {

    private final Faker faker = new Faker(new Locale("es", "cl"));
    private final Random random = new Random();

    @Autowired
    private  ProductoService productoService;

    @Override
    public void run(String... args) throws Exception{
        for(int i=0; i<15; i++){
            Producto productoFicticio = new Producto();
            productoFicticio.setIdProducto(generarIdProducto());
            productoFicticio.setNombreProducto(faker.commerce().productName());
            productoFicticio.setCategoria(generarCategoria());
            productoFicticio.setStock(faker.number().numberBetween(7, 100));
            productoFicticio.setPrecio(faker.number().numberBetween(5000, 100001));
            productoFicticio.setFechaCaducidad(LocalDate.now().minusDays(random.nextInt(60)));

            productoService.guardar(productoFicticio);
            System.out.println("Producto Registrado: "+productoFicticio.getIdProducto());
        }


    }

    private static long idProductoActual = 100; // valor inicial
    private Long generarIdProducto() {
        idProductoActual += 2;
        return idProductoActual;
    }






    private String generarCategoria() {
        String[] metodos = {"Perfumes",
                            "Cuidado Personal",
                            "Belleza",
                            "Cosmética",
                            "Aromaterapia",
                            "Accesorios",
                            "Fragancias Unisex"};
        return metodos[new Random().nextInt(metodos.length)];
    }

    // private String generarDireccionEnvio() {
    //     String[] calles = {"Av. Providencia", "Calle Los Pinos", "Av. Libertador", "Pasaje Las Flores", "Camino Real"};
    //     int numero = new Random().nextInt(1000) + 1;
    //     String ciudad = new String[] {"Santiago", "Valparaíso", "Concepción", "La Serena", "Antofagasta"}[new Random().nextInt(5)];
    //     return calles[new Random().nextInt(calles.length)] + " #" + numero + ", " + ciudad;
    // }



}
