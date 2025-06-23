package com.padima.microservicioreporte.model;


import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;
@Entity
@Table(name="REPORTE")
@Data
public class Reporte {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipoReporte; // "VENTAS", "USUARIOS", etc.
    
    @Column(nullable = false)
    private String formato; // PDF, EXCEL
    
    @Column(nullable = false)
    private Instant fechaGeneracion;
    
    @Column(nullable = false)
    private String estado; // "GENERADO", "FALLIDO", "EN_PROCESO"
    
}