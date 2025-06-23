package com.padima.microservicionotificacion.assembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.padima.microservicionotificacion.controller.NotificacionController;
import com.padima.microservicionotificacion.model.Notificacion;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class NotificacionModelAssembler  implements RepresentationModelAssembler <Notificacion, EntityModel<Notificacion>>{

    @Override
    public EntityModel<Notificacion> toModel(Notificacion n){
        return EntityModel.of(
            n,
            linkTo(methodOn(NotificacionController.class).buscarNotificacion(n.getId())).withRel("Se busca NOTIFICACION por su ID en el Sistema "),
            linkTo(methodOn(NotificacionController.class).listarNotifaciones()).withRel("Se lista todos las NOTIFICACIONES en el Sistema"),
            linkTo(methodOn(NotificacionController.class).eliminarNotificacion(n.getId())).withRel("Se elimina NOTIFICACION por su ID en el Sistema"),
            linkTo(methodOn(NotificacionController.class).actualizarNotificacion(n.getId(),n)).withRel("Se actualiza NOTIFICACION por su ID en el Sistema")
        );
    }


}
