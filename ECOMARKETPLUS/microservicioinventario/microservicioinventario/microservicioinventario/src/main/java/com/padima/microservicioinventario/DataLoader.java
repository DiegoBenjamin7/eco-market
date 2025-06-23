package com.padima.microservicioinventario;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.padima.microservicioinventario.model.Inventario;
import com.padima.microservicioinventario.service.InventarioService;

import net.datafaker.Faker;

@Component
public class DataLoader implements CommandLineRunner {

    private final Faker faker = new Faker(new Locale("es", "cl"));


    @Autowired
    private  InventarioService inventarioService;

    @Override
    public void run(String... args) throws Exception{
        for(int i=0; i<30; i++){
            Inventario inventarioFicticio = new Inventario();
            inventarioFicticio.setIdinventario(generarIdInventario());
            inventarioFicticio.setIdProducto(faker.number().numberBetween(5, 50));
            inventarioFicticio.setStockActual(faker.number().numberBetween(30, 100));
            inventarioFicticio.setUbicacionBodega(generarUbicacionBodega());

            inventarioService.guardar(inventarioFicticio);
            System.out.println("Inventario Registrado: "+inventarioFicticio.getIdinventario());
        }


    }

    private static long idInventarioActual = 100; 
    private Long generarIdInventario() {
        idInventarioActual+= 2;
        return idInventarioActual;
    }


    public String generarUbicacionBodega() {
        String[] ubicaciones = {
            "Bodega Central, Santiago",
            "Centro Logístico Norte, Quilicura",
            "CD Regional, Talca",
            "Hub Logístico Patagonia, Punta Arenas",
            "Bodega Express, Chillán",
            "Centro de Abastecimiento, Iquique"
        };
        return faker.options().option(ubicaciones);
    }


}
