package com.padima.microservicioreporte.service;

import com.padima.microservicioreporte.model.Reporte;
import com.padima.microservicioreporte.repository.ReporteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReporteService {
    @Autowired
    private ReporteRepository reporteRepository;

    // Método existente
    public Reporte generarReporte(String tipo, String formato) {
        Reporte reporte = new Reporte();
        reporte.setTipoReporte(tipo);
        reporte.setFormato(formato);
        reporte.setFechaGeneracion(Instant.now());
        reporte.setEstado("EN_PROCESO");
        
        try {
            Thread.sleep(2000);
            reporte.setEstado("GENERADO");
        } catch (InterruptedException e) {
            reporte.setEstado("FALLIDO");
        }
        
        return reporteRepository.save(reporte);
    }

    // Nuevo: Obtener todos los reportes
    public List<Reporte> getAllReportes() {
        return reporteRepository.findAll();
    }

    // Nuevo: Obtener un reporte por ID
    public Reporte getReporteById(Long id) {
        return reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado con id: " + id));
    }

    // Nuevo: Actualizar un reporte
    public Reporte updateReporte(Long id, Reporte reporteDetails) {
        Reporte reporte = getReporteById(id);
        
        reporte.setTipoReporte(reporteDetails.getTipoReporte());
        reporte.setFormato(reporteDetails.getFormato());
        reporte.setEstado(reporteDetails.getEstado());
        
        return reporteRepository.save(reporte);
    }

    // Nuevo: Eliminar un reporte
    public void deleteReporte(Long id) {
        Reporte reporte = getReporteById(id);
        reporteRepository.delete(reporte);
    }
}
