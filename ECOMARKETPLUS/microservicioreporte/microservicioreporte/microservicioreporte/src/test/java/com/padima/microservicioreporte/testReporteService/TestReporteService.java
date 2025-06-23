package com.padima.microservicioreporte.testReporteService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.padima.microservicioreporte.model.Reporte;
import com.padima.microservicioreporte.repository.ReporteRepository;
import com.padima.microservicioreporte.service.ReporteService;

@ExtendWith(MockitoExtension.class)
public class TestReporteService {

    @Mock
    private ReporteRepository reporteRepository;

    @InjectMocks
    private ReporteService reporteService;

    @Test
    public void testGenerarReporteExitoso() {
        // Configuración
        Reporte reporteMock = crearReporteEjemplo("GENERADO");
        when(reporteRepository.save(any(Reporte.class))).thenReturn(reporteMock);

        // Ejecución
        Reporte resultado = reporteService.generarReporte("VENTAS", "PDF");

        // Verificación
        assertEquals("VENTAS", resultado.getTipoReporte());
        assertEquals("PDF", resultado.getFormato());
        verify(reporteRepository, times(1)).save(any(Reporte.class));
    }

    @Test
    public void testGenerarReporteFallido() {
        // Configuración
        Reporte reporteMock = crearReporteEjemplo("FALLIDO");
        when(reporteRepository.save(any(Reporte.class))).thenReturn(reporteMock);
        Reporte resultado = reporteService.generarReporte("VENTAS", "EXCEL");

        // Verificación
        assertNotNull(resultado.getFechaGeneracion());
        verify(reporteRepository, times(1)).save(any(Reporte.class));
    }

    @Test
    public void testBuscarReportePorId() {
        // Configuración
        Long reporteId = 1L;
        Reporte reporteMock = crearReporteEjemplo("GENERADO");
        when(reporteRepository.findById(reporteId)).thenReturn(Optional.of(reporteMock));

        // Ejecución
        Optional<Reporte> resultado = reporteRepository.findById(reporteId);

        // Verificación
        assertTrue(resultado.isPresent());
        assertEquals("VENTAS", resultado.get().getTipoReporte());
    }

    // Método auxiliar para crear reportes de prueba
    private Reporte crearReporteEjemplo(String estado) {
        Reporte reporte = new Reporte();
        reporte.setId(1L);
        reporte.setTipoReporte("VENTAS");
        reporte.setFormato("PDF");
        reporte.setFechaGeneracion(Instant.now());
        reporte.setEstado(estado);
        return reporte;
    }
}
