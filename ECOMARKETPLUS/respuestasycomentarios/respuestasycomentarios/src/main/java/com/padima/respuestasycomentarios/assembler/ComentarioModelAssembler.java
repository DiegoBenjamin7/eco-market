package com.padima.respuestasycomentarios.assembler;

import com.padima.respuestasycomentarios.controller.ComentarioController;
import com.padima.respuestasycomentarios.model.Comentario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ComentarioModelAssembler implements RepresentationModelAssembler<Comentario, EntityModel<Comentario>> {
    @Override
    @NonNull
    public EntityModel<Comentario> toModel(@NonNull Comentario comentario) {
        return EntityModel.of(comentario,
            linkTo(methodOn(ComentarioController.class).obtenerPorId(comentario.getId())).withSelfRel(),
            linkTo(methodOn(ComentarioController.class).obtenerTodos()).withRel("todos-los-comentarios"),
            linkTo(methodOn(ComentarioController.class).darLike(comentario.getId())).withRel("dar-like")
        );
    }
    }

