package com.padima.microservicioreporte.controller;

import com.padima.microservicioreporte.model.Reporte;
import com.padima.microservicioreporte.service.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/reportes")
@Tag(name = "Reportes", description = "API para gestión completa de reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    // Operación existente para generar reporte
    @PostMapping("/generar")
    @Operation(summary = "Generar nuevo reporte")
    public ResponseEntity<Reporte> generarReporte(
            @RequestParam String tipo,
            @RequestParam String formato) {
        Reporte reporte = reporteService.generarReporte(tipo, formato);
        return ResponseEntity.ok(reporte);
    }

    // Operación existente para descargar reporte
    @GetMapping("/descargar/{id}")
    @Operation(summary = "Descargar reporte generado")
    public ResponseEntity<String> descargarReporte(@PathVariable Long id) {
        return ResponseEntity.ok("Reporte #" + id + " listo para descargar");
    }

    // Nuevo: Obtener todos los reportes
    @GetMapping
    @Operation(summary = "Obtener todos los reportes")
    public ResponseEntity<CollectionModel<EntityModel<Reporte>>> getAllReportes() {
        List<EntityModel<Reporte>> reportes = reporteService.getAllReportes().stream()
                .map(reporte -> EntityModel.of(reporte,
                        linkTo(methodOn(ReporteController.class).getReporteById(reporte.getId())).withSelfRel(),
                        linkTo(methodOn(ReporteController.class).getAllReportes()).withRel("reportes")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(reportes,
                linkTo(methodOn(ReporteController.class).getAllReportes()).withSelfRel()));
    }

    // Nuevo: Obtener un reporte por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener un reporte por ID")
    public ResponseEntity<EntityModel<Reporte>> getReporteById(@PathVariable Long id) {
        Reporte reporte = reporteService.getReporteById(id);
        return ResponseEntity.ok(EntityModel.of(reporte,
                linkTo(methodOn(ReporteController.class).getReporteById(id)).withSelfRel(),
                linkTo(methodOn(ReporteController.class).getAllReportes()).withRel("reportes")));
    }

    // Nuevo: Actualizar un reporte
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un reporte existente")
    public ResponseEntity<Reporte> updateReporte(@PathVariable Long id, @RequestBody Reporte reporteDetails) {
        Reporte updatedReporte = reporteService.updateReporte(id, reporteDetails);
        return ResponseEntity.ok(updatedReporte);
    }

    // Nuevo: Eliminar un reporte
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un reporte")
    public ResponseEntity<?> deleteReporte(@PathVariable Long id) {
        reporteService.deleteReporte(id);
        return ResponseEntity.noContent().build();
    }
}
