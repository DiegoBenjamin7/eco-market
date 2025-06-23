package com.padima.microservicioenvio.assembler;

import com.padima.microservicioenvio.controller.EnvioController;
import com.padima.microservicioenvio.model.Envio;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EnvioModelAssembler implements RepresentationModelAssembler<Envio, EntityModel<Envio>> {

    @Override
    public EntityModel<Envio> toModel(Envio envio) {
        return EntityModel.of(envio,
            // Links CRUD básicos
            linkTo(methodOn(EnvioController.class).obtenerEnvio(envio.getId())).withSelfRel()
                .withType("GET"),
            linkTo(methodOn(EnvioController.class).listarEnvios()).withRel("todos-los-envios")
                .withType("GET"),
            linkTo(methodOn(EnvioController.class).actualizarEnvio(envio.getId(), null)).withRel("actualizar")
                .withType("PUT"),
            linkTo(methodOn(EnvioController.class).eliminarEnvio(envio.getId())).withRel("eliminar")
                .withType("DELETE"),
            
            // Links para operaciones específicas del controller
            linkTo(methodOn(EnvioController.class).cambiarEstado(envio.getId(), null)).withRel("cambiar-estado")
                .withType("POST"),
            linkTo(methodOn(EnvioController.class).obtenerPorEstado(envio.getEstado())).withRel("por-estado")
                .withType("GET"),
            
            // Link para búsqueda (asumiendo que codigoEnvio existe en el modelo)
            linkTo(methodOn(EnvioController.class).buscarEnvios(envio.getCodigoEnvio(), null, null))
                .withRel("buscar-por-codigo")
                .withType("GET")
        );
    }
}
