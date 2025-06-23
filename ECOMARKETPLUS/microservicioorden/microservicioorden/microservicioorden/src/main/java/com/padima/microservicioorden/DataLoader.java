package com.padima.microservicioorden;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.padima.microservicioorden.model.Orden;
import com.padima.microservicioorden.service.OrdenService;

import net.datafaker.Faker;

@Component
public class DataLoader implements CommandLineRunner {

    private final Faker faker = new Faker(new Locale("es", "cl"));
    private final Random random = new Random();

    @Autowired
    private  OrdenService ordenService;

    @Override
    public void run(String... args) throws Exception{
        for(int i=0; i<15; i++){
            Orden ordenFicticio = new Orden();
            ordenFicticio.setIdorden(generarIdOrden());
            ordenFicticio.setRunusuario(generarRutUsuario());
            ordenFicticio.setProductos(faker.commerce().productName());
            ordenFicticio.setTotal(generarPrecioTipoRetail());
            ordenFicticio.setFechaOrden(LocalDate.now().minusDays(random.nextInt(60)));

            ordenService.guardar(ordenFicticio);
            System.out.println("Orden Registrado: "+ordenFicticio.getIdorden());
        }


    }

    private static long idOrdeActual = 200; // valor inicial
    private Long generarIdOrden() {
        idOrdeActual += 6;
        return idOrdeActual;
    }

    private String generarRutUsuario(){
        int cuerpo = 10000000 + random.nextInt(8999999);
        String dv = calcularDv(cuerpo);
        return cuerpo + "-"+dv;
    }

    private String  calcularDv(int cuerpo){
        int m = 0, s = 1;
        while (cuerpo != 0) {
            s = (s + cuerpo % 10 * (9 - m++ % 6)) % 11;
            cuerpo /= 10;
        }

        if (s == 0) return "K";
        if (s == 1) return "0";
        return String.valueOf(11 - s);
    }

    public int generarPrecioTipoRetail() {
        int base = faker.number().numberBetween(10, 100); 
        return (base * 1000) - 10;
    }

}
