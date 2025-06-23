package com.padima.microservicioorden.OrdenServiceTest;

import com.padima.microservicioorden.DTO.UsuarioDTO;
import com.padima.microservicioorden.model.Orden;
import com.padima.microservicioorden.repository.OrdenRepository;
import com.padima.microservicioorden.service.OrdenService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrdenServiceTest {

    @Mock
    private OrdenRepository ordenRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private OrdenService ordenService;

    private Orden orden;
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        orden = new Orden();
        orden.setIdorden(1L);
        orden.setRunusuario("27892745-4");
        orden.setProductos("Producto1,Producto2");
        orden.setTotal(29990);
        orden.setFechaOrden(LocalDate.now());

        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setRun(25L);
        usuarioDTO.setNombre("Diego");
        usuarioDTO.setApellido("Sepulveda");
        usuarioDTO.setContacto(999999999L);
    }

    @Test
    void testBuscarTodo() {
        // Arrange
        when(ordenRepository.findAll()).thenReturn(Arrays.asList(orden));

        // Act
        List<Orden> result = ordenService.buscarTodo();

        // Assert
        assertEquals(1, result.size());
        assertEquals(orden, result.get(0));
        verify(ordenRepository, times(1)).findAll();
    }

    @Test
    void testBuscar() {
        // Arrange
        when(ordenRepository.findById(1L)).thenReturn(Optional.of(orden));

        // Act
        Orden result = ordenService.buscar(1L);

        // Assert
        assertNotNull(result);
        assertEquals(orden, result);
        verify(ordenRepository, times(1)).findById(1L);
    }

    @Test
    void testGuardar() {
        // Arrange
        when(ordenRepository.save(any(Orden.class))).thenReturn(orden);

        // Act
        Orden result = ordenService.guardar(orden);

        // Assert
        assertNotNull(result);
        assertEquals(orden, result);
        verify(ordenRepository, times(1)).save(orden);
    }

    @Test
    void testEliminar() {
        // Arrange
        doNothing().when(ordenRepository).deleteById(1L);

        // Act
        ordenService.eliminar(1L);

        // Assert
        verify(ordenRepository, times(1)).deleteById(1L);
    }

    @Test
    void testBuscarUsuario() {
        // Arrange
        String rut = "27892745-4";
        
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), any(Object.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(UsuarioDTO.class)).thenReturn(Mono.just(usuarioDTO));

        // Act
        UsuarioDTO result = ordenService.BuscarUsuario(rut);

        // Assert
        assertNotNull(result);
        assertEquals(usuarioDTO, result);
    }
}
