package com.padima.microserviciologin;

import java.time.LocalDate;
import java.util.Locale;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.padima.microserviciologin.model.Login;
import com.padima.microserviciologin.service.LoginService;

import net.datafaker.Faker;

@Component
public class DataLoader implements CommandLineRunner {

    private final Faker faker = new Faker(new Locale("es", "cl"));

    @Autowired
    private  LoginService loginService;

    @Override
    public void run(String... args) throws Exception{
        for(int i=0; i<20; i++){
            Login loginFicticio = new Login();
            loginFicticio.setId(generarIdLogin());
            loginFicticio.setEmailUsuario(faker.internet().emailAddress());
            loginFicticio.setPassword(faker.internet().password());
            loginFicticio.setEstadoSesion(faker.address().streetAddress());
            loginFicticio.setFechaInicio(faker.date().past(1, TimeUnit.DAYS).toInstant());
            loginFicticio.setFechaTermino(faker.date().future(1, TimeUnit.DAYS).toInstant());

            loginService.guardar(loginFicticio);
            System.out.println("Login fue Registrado: "+loginFicticio.getId());
        }


    }

    private static long idLoginActual = 500; // valor inicial

    private Long generarIdLogin() {
        idLoginActual  += 2;
        return idLoginActual ;
    }
}
