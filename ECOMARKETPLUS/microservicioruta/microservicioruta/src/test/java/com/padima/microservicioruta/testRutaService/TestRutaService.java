package com.padima.microservicioruta.testRutaService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.padima.microservicioruta.model.Ruta;
import com.padima.microservicioruta.repository.RutaRepository;
import com.padima.microservicioruta.service.RutaService;

@ExtendWith(MockitoExtension.class)
public class TestRutaService {

    @Mock
    private RutaRepository rutaRepository;

    @InjectMocks
    private RutaService rutaService;

    @Test
    public void testCrearRutaExitoso() {
        Ruta ruta = new Ruta();
        ruta.setCodigoRuta("RUT-12345");
        
        when(rutaRepository.existsByCodigoRuta("RUT-12345")).thenReturn(false);
        when(rutaRepository.save(ruta)).thenReturn(ruta);
        
        Ruta resultado = rutaService.crearRuta(ruta);
        
        assertEquals("ACTIVA", resultado.getEstado());
        verify(rutaRepository, times(1)).save(ruta);
    }

    @Test
    public void testCrearRutaConCodigoDuplicado() {
        Ruta ruta = new Ruta();
        ruta.setCodigoRuta("RUT-12345");
        
        when(rutaRepository.existsByCodigoRuta("RUT-12345")).thenReturn(true);
        
        assertThrows(IllegalStateException.class, () -> {
            rutaService.crearRuta(ruta);
        });
    }

    @Test
    public void testObtenerRutasActivas() {
        List<Ruta> rutasActivas = Arrays.asList(
            crearRutaEjemplo("ACTIVA"),
            crearRutaEjemplo("ACTIVA")
        );
        
        when(rutaRepository.findByEstado("ACTIVA")).thenReturn(rutasActivas);
        
        List<Ruta> resultado = rutaService.obtenerRutasActivas();
        
        assertEquals(2, resultado.size());
        resultado.forEach(r -> assertEquals("ACTIVA", r.getEstado()));
    }

    @Test
    public void testCambiarEstadoRuta() {
        Long rutaId = 1L;
        Ruta ruta = crearRutaEjemplo("ACTIVA");
        
        when(rutaRepository.findById(rutaId)).thenReturn(Optional.of(ruta));
        when(rutaRepository.save(ruta)).thenReturn(ruta);
        
        Ruta resultado = rutaService.cambiarEstadoRuta(rutaId, "INACTIVA");
        
        assertEquals("INACTIVA", resultado.getEstado());
        verify(rutaRepository, times(1)).save(ruta);
    }

    private Ruta crearRutaEjemplo(String estado) {
        Ruta ruta = new Ruta();
        ruta.setId(1L);
        ruta.setCodigoRuta("RUT-TEST");
        ruta.setOrigen("Santiago");
        ruta.setDestino("Valparaíso");
        ruta.setDistanciaKm(100.0);
        ruta.setTiempoEstimadoMinutos(120);
        ruta.setTipoTransporte("TERRESTRE");
        ruta.setEstado(estado);
        return ruta;
    }
}
