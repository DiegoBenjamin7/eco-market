package com.padima.microserviciologin.testLoginService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.padima.microserviciologin.repository.LoginRepository;
import com.padima.microserviciologin.service.LoginService;
import com.padima.microserviciologin.model.Login;



@ExtendWith(MockitoExtension.class)
public class TestLoginService {

    @Mock
    private LoginRepository loginRepository;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBuscarTodo(){
        List<Login> listaLogin = new ArrayList<>();


        Login l1 = new Login();

        l1.setId(1L);
        l1.setEmailUsuario("diego@duocuc.cl");
        l1.setPassword("Padima77777");
        l1.setEstadoSesion("ACTIVA");
        l1.setFechaInicio(LocalDate.parse("2021-09-12")
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant());

        l1.setFechaTermino(LocalDate.parse("2026-09-12")
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant());


        listaLogin.add(l1);

        when(loginRepository.findAll()).thenReturn(listaLogin);
        List<Login> loginBuscada = loginService.buscarTodo();
        assertEquals(1, loginBuscada.size());
        verify(loginRepository, times(1)).findAll();

    }

    @Test
    public void testBuscarUnLogin(){
        Login l1 = new Login();
        l1.setId(1L);
        l1.setEmailUsuario("diego@duocuc.cl");
        l1.setPassword("Padima77777");
        l1.setEstadoSesion("ACTIVA");
        l1.setFechaInicio(LocalDate.parse("2021-09-12")
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant());
        l1.setFechaTermino(LocalDate.parse("2026-09-12")
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant());

        when(loginRepository.findById(1L)).thenReturn(Optional.of(l1));
        Login loginBuscada = loginService.buscar(1L);
        assertEquals(1, loginBuscada.getId());
        verify(loginRepository, times(1)).findById(1L);
    }

    @Test
    public void testGuardarLogin(){
        Login l1 = new Login();

        l1.setId(1L);
        l1.setEmailUsuario("diego@duocuc.cl");
        l1.setPassword("Padima77777");
        l1.setEstadoSesion("ACTIVA");
        l1.setFechaInicio(LocalDate.parse("2021-09-12")
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant());
        l1.setFechaTermino(LocalDate.parse("2026-09-12")
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant());

        when(loginRepository.save(l1)).thenReturn(l1);
        Login loginGuardada = loginService.guardar(l1);
        assertEquals(1, loginGuardada.getId());
        verify(loginRepository, times(1)).save(l1);
    }

    @Test
    public void testEliminarLogin(){
        Long idLogin = 1L; 
        doNothing().when(loginRepository).deleteById(idLogin);

        loginService.eliminar(idLogin);
        verify(loginRepository, times(1)).deleteById(idLogin);
    }

}
