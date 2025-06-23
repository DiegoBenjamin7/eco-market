package com.padima.microserviciousuario.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.padima.microserviciousuario.controller.UsuarioController;
import com.padima.microserviciousuario.model.Usuario;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(
            usuario,
            linkTo(methodOn(UsuarioController.class).BuscarUsuario(usuario.getRut())).withSelfRel(),
            linkTo(methodOn(UsuarioController.class).ListarUsuario()).withRel("all-users"),
            linkTo(methodOn(UsuarioController.class).eliminarUsuario(usuario.getRut())).withRel("delete"),
            linkTo(methodOn(UsuarioController.class).actualizar(usuario.getRut(), usuario)).withRel("update")
        );
    }
}
