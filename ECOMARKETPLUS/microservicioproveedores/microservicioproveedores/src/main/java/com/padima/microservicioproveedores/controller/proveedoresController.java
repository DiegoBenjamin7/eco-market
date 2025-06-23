package com.padima.microservicioproveedores.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.padima.microservicioproveedores.assembler.proveedoresAssembler;
import com.padima.microservicioproveedores.model.proveedores;
import com.padima.microservicioproveedores.service.proveedoresService;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/proveedores")
public class proveedoresController {

    private final proveedoresService proveedoresService;
    private final proveedoresAssembler assembler;

    public proveedoresController(proveedoresService proveedoresService, proveedoresAssembler assembler) {
        this.proveedoresService = proveedoresService;
        this.assembler = assembler;
    }

    @PostMapping
    public ResponseEntity<EntityModel<proveedores>> crearProveedor(@RequestBody proveedores nuevoProveedor) {
        proveedores proveedorCreado = proveedoresService.crearProveedor(nuevoProveedor);
        EntityModel<proveedores> entityModel = assembler.toModel(proveedorCreado);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/{id}")
    public EntityModel<proveedores> buscarProveedorPorId(@PathVariable Long id) {
        proveedores proveedor = proveedoresService.buscarProveedorPorId(id);
        return assembler.toModel(proveedor);
    }

    @GetMapping
    public CollectionModel<EntityModel<proveedores>> listarProveedores(
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String nombre) {
        
        List<proveedores> proveedoresFiltrados;
        
        if (tipo != null && nombre != null) {
            proveedoresFiltrados = proveedoresService.buscarPorTipoYNombre(tipo, nombre);
        } else if (tipo != null) {
            proveedoresFiltrados = proveedoresService.buscarProveedoresPorTipo(tipo);
        } else if (nombre != null) {
            proveedoresFiltrados = proveedoresService.buscarPorNombreContaining(nombre);
        } else {
            proveedoresFiltrados = proveedoresService.obtenerTodosProveedores();
        }
        
        List<EntityModel<proveedores>> proveedores = proveedoresFiltrados.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(proveedores,
                linkTo(methodOn(proveedoresController.class).listarProveedores(tipo, nombre)).withSelfRel());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<proveedores>> actualizarProveedor(
            @PathVariable Long id,
            @RequestBody proveedores proveedorActualizado) {
        proveedores proveedor = proveedoresService.actualizarProveedor(id, proveedorActualizado);
        EntityModel<proveedores> entityModel = assembler.toModel(proveedor);
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProveedor(@PathVariable Long id) {
        proveedoresService.eliminarProveedor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/porTipo/{tipo}")
    public CollectionModel<EntityModel<proveedores>> buscarPorTipo(@PathVariable String tipo) {
        List<EntityModel<proveedores>> proveedores = proveedoresService.buscarProveedoresPorTipo(tipo).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(proveedores,
                linkTo(methodOn(proveedoresController.class).buscarPorTipo(tipo)).withSelfRel());
    }
}