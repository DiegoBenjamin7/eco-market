package com.padima.microservicioenvio.testEnvioService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.padima.microservicioenvio.model.Envio;
import com.padima.microservicioenvio.repository.EnvioRepository;
import com.padima.microservicioenvio.service.EnvioService;

@ExtendWith(MockitoExtension.class)
public class TestEnvioService {

    @Mock
    private EnvioRepository envioRepository;

    @InjectMocks
    private EnvioService envioService;

    @Test
    public void testCrearEnvioExitoso() {
        Envio envio = new Envio();
        envio.setCodigoEnvio("ENVIO N°-1234");

        when(envioRepository.existsByCodigoEnvio("ENVIO N°-1234")).thenReturn(false);
        // Simula que el repositorio asigna un estado por defecto si es necesario
        envio.setEstado("PREPARACION");
        when(envioRepository.save(any(Envio.class))).thenReturn(envio);

        Envio resultado = envioService.crearEnvio(envio);

        assertEquals("PREPARACION", resultado.getEstado());
        verify(envioRepository, times(1)).save(any(Envio.class));
    }

    @Test
    public void testCrearEnvioConCodigoDuplicado() {
        Envio envio = new Envio();
        envio.setCodigoEnvio("ENVIO N°-1234");

        when(envioRepository.existsByCodigoEnvio("ENVIO N°-1234")).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> {
            envioService.crearEnvio(envio);
        });
    }

    @Test
    public void testObtenerEnviosPorEstado() {
        List<Envio> enviosActivos = Arrays.asList(
            crearEnvioEjemplo("EN_TRANSITO"),
            crearEnvioEjemplo("EN_TRANSITO")
        );
        
        when(envioRepository.findByEstado("EN_TRANSITO")).thenReturn(enviosActivos);
        
        List<Envio> resultado = envioService.obtenerEnviosPorEstado("EN_TRANSITO");
        
        assertEquals(2, resultado.size());
        resultado.forEach(e -> assertEquals("EN_TRANSITO", e.getEstado()));
    }

    @Test
    public void testCambiarEstadoEnvio() {
        Long envioId = 1L;
        Envio envio = crearEnvioEjemplo("PREPARACION");
        
        when(envioRepository.findById(envioId)).thenReturn(Optional.of(envio));
        when(envioRepository.save(envio)).thenReturn(envio);
        
        Envio resultado = envioService.cambiarEstadoEnvio(envioId, "ENTREGADO");
        
        assertEquals("ENTREGADO", resultado.getEstado());
        verify(envioRepository, times(1)).save(envio);
    }

    private Envio crearEnvioEjemplo(String estado) {
        Envio envio = new Envio();
        envio.setId(1L);
        envio.setCodigoEnvio("ENV-TEST");
        envio.setIdRuta(1L);
        envio.setIdPedido(1000L);
        envio.setEstado(estado);
        envio.setPesoKg(10.0);
        envio.setDireccionDestino("Calle Falsa 123");
        return envio;
    }
}
