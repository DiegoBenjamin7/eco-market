package com.padima.microservicioruta.controller;

import com.padima.microservicioruta.assembler.RutaModelAssembler;
import com.padima.microservicioruta.model.Ruta;
import com.padima.microservicioruta.service.RutaService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/rutas")
public class RutaController {

    private final RutaService rutaService;
    private final RutaModelAssembler assembler;

    public RutaController(RutaService rutaService, RutaModelAssembler assembler) {
        this.rutaService = rutaService;
        this.assembler = assembler;
    }

    // Crear una nueva ruta
    @PostMapping
    public ResponseEntity<EntityModel<Ruta>> crearRuta(@RequestBody Ruta nuevaRuta) {
        Ruta rutaCreada = rutaService.crearRuta(nuevaRuta);
        EntityModel<Ruta> entityModel = assembler.toModel(rutaCreada);
        
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    // Obtener una ruta por ID
    @GetMapping("/{id}")
    public EntityModel<Ruta> buscarRutaPorId(@PathVariable Long id) {
        Ruta ruta = rutaService.buscarRutaPorId(id);
        return assembler.toModel(ruta);
    }

    // Listar todas las rutas (con filtros opcionales)
    @GetMapping
    public CollectionModel<EntityModel<Ruta>> buscarRutas(
            @RequestParam(required = false) String origen,
            @RequestParam(required = false) String destino,
            @RequestParam(required = false) String tipoTransporte) {
        
        List<EntityModel<Ruta>> rutas = rutaService.buscarRutasDisponibles(origen, destino, tipoTransporte)
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(rutas,
                linkTo(methodOn(RutaController.class).buscarRutas(origen, destino, tipoTransporte)).withSelfRel());
    }

    // Obtener rutas activas
    @GetMapping("/activas")
    public CollectionModel<EntityModel<Ruta>> obtenerRutasActivas() {
        List<EntityModel<Ruta>> rutas = rutaService.obtenerRutasActivas()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(rutas,
                linkTo(methodOn(RutaController.class).obtenerRutasActivas()).withSelfRel());
    }

    // Actualizar una ruta completa
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Ruta>> actualizarRuta(
            @PathVariable Long id,
            @RequestBody Ruta rutaActualizada) {
        
        Ruta ruta = rutaService.actualizarRuta(id, rutaActualizada);
        EntityModel<Ruta> entityModel = assembler.toModel(ruta);
        
        return ResponseEntity.ok(entityModel);
    }

    // Cambiar estado de una ruta
    @PutMapping("/{id}/estado")
    public ResponseEntity<EntityModel<Ruta>> cambiarEstado(
            @PathVariable Long id,
            @RequestParam String estado) {
        
        Ruta ruta = rutaService.cambiarEstadoRuta(id, estado);
        EntityModel<Ruta> entityModel = assembler.toModel(ruta);
        
        return ResponseEntity.ok(entityModel);
    }

    // Eliminar una ruta
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarRuta(@PathVariable Long id) {
        rutaService.eliminarRuta(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint adicional: Buscar rutas por tipo de transporte
    @GetMapping("/por-transporte/{tipoTransporte}")
    public CollectionModel<EntityModel<Ruta>> buscarPorTipoTransporte(
            @PathVariable String tipoTransporte) {
        
        List<EntityModel<Ruta>> rutas = rutaService.buscarPorTipoTransporte(tipoTransporte)
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(rutas,
                linkTo(methodOn(RutaController.class).buscarPorTipoTransporte(tipoTransporte)).withSelfRel());
    }
}