package com.padima.microservicioreporte.repository;
import com.padima.microservicioreporte.model.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
}