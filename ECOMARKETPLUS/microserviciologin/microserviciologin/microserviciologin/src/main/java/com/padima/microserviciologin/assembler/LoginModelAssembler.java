package com.padima.microserviciologin.assembler;


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import com.padima.microserviciologin.controller.LoginController;

import com.padima.microserviciologin.model.Login;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class LoginModelAssembler  implements RepresentationModelAssembler <Login, EntityModel<Login>>{

    @Override
    public EntityModel<Login> toModel(Login l){
        return EntityModel.of(
            l,
            linkTo(methodOn(LoginController.class).buscarLogin(l.getId())).withRel("Busca un Login del Sistema"),
            linkTo(methodOn(LoginController.class).listarLogins()).withRel("Listar todos los Logins del Sistema"),
            linkTo(methodOn(LoginController.class).eliminarLogin(l.getId())).withRel("Se elimina un Login del Sistema"),
            linkTo(methodOn(LoginController.class).actualizarLogin(l.getId(),l)).withRel("Se actualiza un Login del Sistema")
        );
    }

}
