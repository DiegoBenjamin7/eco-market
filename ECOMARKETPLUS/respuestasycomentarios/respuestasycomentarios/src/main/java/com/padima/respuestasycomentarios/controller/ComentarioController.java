package com.padima.respuestasycomentarios.controller;

import com.padima.respuestasycomentarios.assembler.ComentarioModelAssembler;
import com.padima.respuestasycomentarios.model.Comentario;
import com.padima.respuestasycomentarios.service.ComentarioService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {
    private final ComentarioService comentarioService;
    private final ComentarioModelAssembler assembler;

    public ComentarioController(ComentarioService comentarioService, ComentarioModelAssembler assembler) {
        this.comentarioService = comentarioService;
        this.assembler = assembler;
    }

    // POST - Crear comentario
    @PostMapping
    public ResponseEntity<EntityModel<Comentario>> crearComentario(@RequestBody Comentario comentario) {
        Comentario nuevoComentario = comentarioService.crearComentario(comentario);
        EntityModel<Comentario> model = assembler.toModel(nuevoComentario);
        return ResponseEntity.created(model.getRequiredLink("self").toUri()).body(model);
    }

    // GET - Todos los comentarios
    @GetMapping
    public CollectionModel<EntityModel<Comentario>> obtenerTodos() {
        List<EntityModel<Comentario>> comentarios = comentarioService.obtenerTodos().stream()
            .map(assembler::toModel)
            .toList();
        return CollectionModel.of(comentarios, linkTo(methodOn(ComentarioController.class).obtenerTodos()).withSelfRel());
    }

    // GET - Comentario por ID
    @GetMapping("/{id}")
    public EntityModel<Comentario> obtenerPorId(@PathVariable Long id) {
        return assembler.toModel(comentarioService.obtenerPorId(id));
    }

    // GET - Comentarios por publicación
    @GetMapping("/publicacion/{publicacionId}")
    public CollectionModel<EntityModel<Comentario>> obtenerPorPublicacion(@PathVariable Long publicacionId) {
        List<EntityModel<Comentario>> comentarios = comentarioService.obtenerPorPublicacion(publicacionId).stream()
            .map(assembler::toModel)
            .toList();
        return CollectionModel.of(comentarios, linkTo(methodOn(ComentarioController.class).obtenerPorPublicacion(publicacionId)).withSelfRel());
    }

    // PUT - Actualizar comentario
    @PutMapping("/{id}")
    public EntityModel<Comentario> actualizarComentario(@PathVariable Long id, @RequestBody String nuevoContenido) {
        return assembler.toModel(comentarioService.actualizarComentario(id, nuevoContenido));
    }

    // PATCH - Dar like
    @PatchMapping("/{id}/like")
    public EntityModel<Comentario> darLike(@PathVariable Long id) {
        return assembler.toModel(comentarioService.darLike(id));
    }

    // DELETE - Eliminar (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarComentario(@PathVariable Long id) {
        comentarioService.eliminarComentario(id);
        return ResponseEntity.noContent().build();
    }
}
