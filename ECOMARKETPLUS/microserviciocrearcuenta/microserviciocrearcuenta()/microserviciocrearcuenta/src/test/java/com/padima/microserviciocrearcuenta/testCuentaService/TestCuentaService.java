package com.padima.microserviciocrearcuenta.testCuentaService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.padima.microserviciocrearcuenta.model.Cuenta;
import com.padima.microserviciocrearcuenta.repository.CuentaRepository;
import com.padima.microserviciocrearcuenta.service.CuentaService;

@ExtendWith(MockitoExtension.class)
public class TestCuentaService {
    @Mock
    private CuentaRepository cuentaRepository;

    @InjectMocks
    private CuentaService cuentaService;

    private Cuenta cuenta;

    @BeforeEach
    public void setUp() {
        cuenta = new Cuenta();
        cuenta.setId(1L);
        cuenta.setUsername("diego");
        cuenta.setEmail("ds.sepulveda@duocuc.cl");
        cuenta.setPassword("Padima77777");
        cuenta.setNombreCompleto("Diego sepulveda");
        cuenta.setEstado("ACTIVA");
        cuenta.setFechaCreacion(Instant.now());
    }

    @Test
    public void testBuscarTodo() {
        List<Cuenta> lista = new ArrayList<>();
        lista.add(cuenta);
        when(cuentaRepository.findAll()).thenReturn(lista);
        
        List<Cuenta> resultado = cuentaService.buscarTodo();
        assertEquals(1, resultado.size());
        verify(cuentaRepository, times(1)).findAll();
    }

    @Test
    public void testBuscar() {
        when(cuentaRepository.findById(1L)).thenReturn(Optional.of(cuenta));
        
        Cuenta resultado = cuentaService.buscar(1L);
        assertNotNull(resultado);
        assertEquals("diegoSepulveda", resultado.getUsername());
        verify(cuentaRepository, times(1)).findById(1L);
    }

    @Test
    public void testGuardar() {
        when(cuentaRepository.save(any(Cuenta.class))).thenReturn(cuenta);
        
        Cuenta resultado = cuentaService.guardar(cuenta);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(cuentaRepository, times(1)).save(cuenta);
    }

    @Test
    public void testEliminar() {
        doNothing().when(cuentaRepository).deleteById(1L);
        
        cuentaService.eliminar(1L);
        verify(cuentaRepository, times(1)).deleteById(1L);
    }
}
