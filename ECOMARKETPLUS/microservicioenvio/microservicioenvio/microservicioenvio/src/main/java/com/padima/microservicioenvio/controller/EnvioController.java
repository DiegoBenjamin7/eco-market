package com.padima.microservicioenvio.controller;

import com.padima.microservicioenvio.model.Envio;
import com.padima.microservicioenvio.service.EnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/envios")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    // ===== ENDPOINTS CRUD =====
    @PostMapping
    public ResponseEntity<Envio> crearEnvio(@RequestBody Envio envio) {
        try {
            Envio nuevoEnvio = envioService.crearEnvio(envio);
            return new ResponseEntity<>(nuevoEnvio, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Envio> obtenerEnvio(@PathVariable Long id) {
        Optional<Envio> envio = envioService.buscarEnvioPorId(id);
        return envio.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Envio>> listarEnvios() {
        return ResponseEntity.ok(envioService.listarTodosLosEnvios());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Envio> actualizarEnvio(
            @PathVariable Long id,
            @RequestBody Envio envioActualizado) {
        try {
            return ResponseEntity.ok(envioService.actualizarEnvio(id, envioActualizado));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEnvio(@PathVariable Long id) {
        try {
            envioService.eliminarEnvio(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ===== ENDPOINTS ESPECÍFICOS (según tu código original) =====
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Envio>> obtenerPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(envioService.obtenerEnviosPorEstado(estado));
    }

    @PostMapping("/{id}/cambiar-estado")
    public ResponseEntity<Envio> cambiarEstado(
            @PathVariable Long id,
            @RequestBody String nuevoEstado) {
        try {
            return ResponseEntity.ok(envioService.cambiarEstadoEnvio(id, nuevoEstado));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Envio>> buscarEnvios(
            @RequestParam(required = false) String codigo,
            @RequestParam(required = false) Long idRuta,
            @RequestParam(required = false) String estado) {
        return ResponseEntity.ok(envioService.buscarEnvios(codigo, idRuta, estado));
    }
}
