package com.padima.microservicioorden.assembler;


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.padima.microservicioorden.controller.OrdenController;
import com.padima.microservicioorden.model.Orden;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class OrdenModelAssembler  implements RepresentationModelAssembler <Orden, EntityModel<Orden>>{

    @Override
    public EntityModel<Orden> toModel(Orden o){
        return EntityModel.of(
            o,
            linkTo(methodOn(OrdenController.class).buscarOrden(o.getIdorden())).withRel("Se busca ORDEN por su ID en el Sistema "),
            linkTo(methodOn(OrdenController.class).listarOrdenes()).withRel("Se lista todos las ORDENES en el Sistema"),
            linkTo(methodOn(OrdenController.class).eliminarOrden(o.getIdorden())).withRel("Se elimina ORDEN por su ID en el Sistema"),
            linkTo(methodOn(OrdenController.class).actualizarOrden(o.getIdorden(),o)).withRel("Se actualiza ORDEN por su ID en el Sistema")
        );
    }


}
