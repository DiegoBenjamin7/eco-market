package com.padima.microserviciofactura.controller;
// src/main/java/com/example/invoiceservice/controller/InvoiceController.java


import com.padima.microserviciofactura.dto.FacturaDTO;
import com.padima.microserviciofactura.service.FacturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ecomarket/facturas")
@Tag(name = "Factura Controller", description = "API para gestión de facturas")
public class FacturaController {
    private final FacturaService facturaService;

    @Autowired
    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @PostMapping
    @Operation(summary = "Crear una nueva factura")
    public ResponseEntity<FacturaDTO> createFactura(@RequestBody FacturaDTO facturaDTO) {
        return ResponseEntity.ok(facturaService.createFactura(facturaDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener factura por la ID")
    public ResponseEntity<FacturaDTO> getFacturaById(@PathVariable Long id) {
        return ResponseEntity.ok(facturaService.getFacturaById(id));
    }

    @GetMapping
    @Operation(summary = "Obtener todas las facturas")
    public ResponseEntity<List<FacturaDTO>> getAllFacturas() {
        return ResponseEntity.ok(facturaService.getAllFacturas());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar factura que ya existe")
    public ResponseEntity<FacturaDTO> updateFactura(
            @PathVariable Long id,
            @RequestBody FacturaDTO facturaDTO) {
        return ResponseEntity.ok(facturaService.updateFactura(id, facturaDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una factura")
    public ResponseEntity<Void> deleteFactura(@PathVariable Long id) {
        facturaService.deleteFactura(id);
        return ResponseEntity.noContent().build();
    }
}
