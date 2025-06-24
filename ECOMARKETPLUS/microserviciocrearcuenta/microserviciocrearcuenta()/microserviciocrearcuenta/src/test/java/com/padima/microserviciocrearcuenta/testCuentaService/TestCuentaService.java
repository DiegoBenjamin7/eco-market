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
        cuenta.setUsername("diego"); // Valor inicializado en setUp
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
        // Corrección: El username esperado debe coincidir con el inicializado en setUp
        assertEquals("diego", resultado.getUsername()); 
        verify(cuentaRepository, times(1)).findById(1L);
    }

    @Test
    public void testGuardar() {
        // Mockear existsByUsername y existsByEmail para que devuelvan false
        when(cuentaRepository.existsByUsername(anyString())).thenReturn(false);
        when(cuentaRepository.existsByEmail(anyString())).thenReturn(false);
        when(cuentaRepository.save(any(Cuenta.class))).thenReturn(cuenta);
        
        Cuenta resultado = cuentaService.guardar(cuenta);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(cuentaRepository, times(1)).save(cuenta);
    }

    @Test
    public void testEliminar() {
        // Mockear existsById para que devuelva true, indicando que la cuenta existe antes de eliminar
        when(cuentaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(cuentaRepository).deleteById(1L);
        
        cuentaService.eliminar(1L);
        verify(cuentaRepository, times(1)).deleteById(1L);

        // Verificar que la cuenta ya no existe después de la eliminación
        when(cuentaRepository.findById(1L)).thenReturn(Optional.empty()); // Simular que no se encuentra
        assertNull(cuentaService.buscar(1L)); // Verificar que buscar devuelve null
    }

    @Test
    public void testActualizar() {
        Cuenta cuentaActualizada = new Cuenta();
        cuentaActualizada.setId(1L);
        cuentaActualizada.setUsername("diegoUpdated");
        cuentaActualizada.setEmail("ds.sepulveda.updated@duocuc.cl");
        cuentaActualizada.setPassword("NewPassword123");
        cuentaActualizada.setNombreCompleto("Diego Sepulveda Updated");
        cuentaActualizada.setEstado("INACTIVA");
        cuentaActualizada.setFechaActualizacion(Instant.now());

        when(cuentaRepository.findById(1L)).thenReturn(Optional.of(cuenta));
        when(cuentaRepository.existsByUsername(anyString())).thenReturn(false);
        when(cuentaRepository.existsByEmail(anyString())).thenReturn(false);
        when(cuentaRepository.save(any(Cuenta.class))).thenReturn(cuentaActualizada);

        Cuenta resultado = cuentaService.actualizar(1L, cuentaActualizada);
        assertNotNull(resultado);
        assertEquals("diegoUpdated", resultado.getUsername());
        assertEquals("ds.sepulveda.updated@duocuc.cl", resultado.getEmail());
        verify(cuentaRepository, times(1)).findById(1L);
        verify(cuentaRepository, times(1)).save(any(Cuenta.class));
    }

    @Test
    public void testGuardar_UsernameExistente() {
        when(cuentaRepository.existsByUsername(cuenta.getUsername())).thenReturn(true);
        
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            cuentaService.guardar(cuenta);
        });
        assertEquals("El username '" + cuenta.getUsername() + "' ya está registrado", thrown.getMessage());
        verify(cuentaRepository, never()).save(any(Cuenta.class));
    }

    @Test
    public void testGuardar_EmailExistente() {
        when(cuentaRepository.existsByUsername(cuenta.getUsername())).thenReturn(false);
        when(cuentaRepository.existsByEmail(cuenta.getEmail())).thenReturn(true);
        
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            cuentaService.guardar(cuenta);
        });
        assertEquals("El email '" + cuenta.getEmail() + "' ya está registrado", thrown.getMessage());
        verify(cuentaRepository, never()).save(any(Cuenta.class));
    }

    @Test
    public void testEliminar_CuentaNoEncontrada() {
        when(cuentaRepository.existsById(1L)).thenReturn(false);
        
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            cuentaService.eliminar(1L);
        });
        assertEquals("Cuenta no encontrada con ID: 1", thrown.getMessage());
        verify(cuentaRepository, never()).deleteById(anyLong());
    }
}
