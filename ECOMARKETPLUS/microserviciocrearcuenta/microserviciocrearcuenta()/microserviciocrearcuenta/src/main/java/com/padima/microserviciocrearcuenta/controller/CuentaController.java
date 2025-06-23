package com.padima.microserviciocrearcuenta.controller;

import com.padima.microserviciocrearcuenta.assembler.CuentaModelAssembler;
import com.padima.microserviciocrearcuenta.model.Cuenta;
import com.padima.microserviciocrearcuenta.service.CuentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ecomarket/cuentas")
@Tag(name = "Cuenta", description = "Endpoints para gestión de cuentas de los usuarios de EcoMarket")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private CuentaModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar todas las cuentas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de las cuentas obtenidas"),
        @ApiResponse(responseCode = "404", description = "Error interno del servidor")
    })
    public ResponseEntity<List<Cuenta>> listarCuentas() {
        List<Cuenta> cuentas = cuentaService.buscarTodo();
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cuenta por el ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La Cuenta fue encontrada"),
        @ApiResponse(responseCode = "404", description = "Cuenta no fue encontrada")
    })
    public ResponseEntity<EntityModel<Cuenta>> buscarCuenta(@PathVariable Long id) {
        Cuenta cuenta = cuentaService.buscar(id);
        if (cuenta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(cuenta));
    }

    @PostMapping
    @Operation(summary = "Crear nueva cuenta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "La Cuenta fue creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Los Datos de la cuenta son inválidos"),
        @ApiResponse(responseCode = "409", description = "El username o email ya existen")
    })
    public ResponseEntity<EntityModel<Cuenta>> crearCuenta(@RequestBody Cuenta cuenta) {
        if (cuentaService.existeUsername(cuenta.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        if (cuentaService.existeEmail(cuenta.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        Cuenta nuevaCuenta = cuentaService.guardar(cuenta);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nuevaCuenta));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar la cuenta existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "La Cuenta fue actualizada"),
        @ApiResponse(responseCode = "404", description = "La Cuenta no fue actualizada encontrada")
    })
    public ResponseEntity<EntityModel<Cuenta>> actualizarCuenta(
            @PathVariable Long id, @RequestBody Cuenta cuenta) {
        Cuenta cuentaActualizada = cuentaService.actualizar(id, cuenta);
        if (cuentaActualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(cuentaActualizada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una cuenta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "La Cuenta fue eliminada"),
        @ApiResponse(responseCode = "404", description = "La Cuenta no fue encontrada")
    })
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id) {
        if (cuentaService.buscar(id) == null) {
            return ResponseEntity.notFound().build();
        }
        cuentaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
