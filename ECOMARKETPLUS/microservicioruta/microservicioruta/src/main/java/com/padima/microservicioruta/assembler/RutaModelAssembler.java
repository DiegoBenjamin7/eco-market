package com.padima.microservicioruta.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import com.padima.microservicioruta.controller.RutaController;
import com.padima.microservicioruta.model.Ruta;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class RutaModelAssembler implements RepresentationModelAssembler<Ruta, EntityModel<Ruta>> {

    @Override
    public EntityModel<Ruta> toModel(Ruta ruta) {
        return EntityModel.of(
            ruta,
            linkTo(methodOn(RutaController.class).buscarRutaPorId(ruta.getId())).withSelfRel(),
            linkTo(methodOn(RutaController.class).obtenerRutasActivas()).withRel("rutas-activas"),
            linkTo(methodOn(RutaController.class).cambiarEstado(ruta.getId(), null)).withRel("cambiar-estado"),
            linkTo(methodOn(RutaController.class).buscarRutas(null, null, null)).withRel("todas-las-rutas")
        );
    }
}