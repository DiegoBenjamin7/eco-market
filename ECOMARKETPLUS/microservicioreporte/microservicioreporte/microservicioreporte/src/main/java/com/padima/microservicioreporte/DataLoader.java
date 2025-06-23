package com.padima.microservicioreporte;
import com.padima.microservicioreporte.model.Reporte;
import com.padima.microservicioreporte.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.Instant;
@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private ReporteService reporteService;
    @Override
    public void run(String... args) throws Exception {
        // Crear algunos reportes de prueba al iniciar
        Reporte reporte1 = new Reporte();
        reporte1.setTipoReporte("VENTAS");
        reporte1.setFormato("PDF");
        reporte1.setFechaGeneracion(Instant.now());
        reporte1.setEstado("GENERADO");
        
        reporteService.generarReporte("VENTAS", "EXCEL");
        reporteService.generarReporte("USUARIOS", "PDF");
    }
}