package com.padima.microservicioinventario.assembler;


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.padima.microservicioinventario.controller.InventarioController;
import com.padima.microservicioinventario.model.Inventario;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class InventarioModelAssembler  implements RepresentationModelAssembler <Inventario, EntityModel<Inventario>>{

    @Override
    public EntityModel<Inventario> toModel(Inventario i){
        return EntityModel.of(
            i,
            linkTo(methodOn(InventarioController.class).buscarInventario(i.getIdinventario())).withRel("Se busca producto del inventario por su ID en el Sistema "),
            linkTo(methodOn(InventarioController.class).listarInventarios()).withRel("Se lista todo el inventario en el Sistema"),
            linkTo(methodOn(InventarioController.class).eliminarInventario(i.getIdinventario())).withRel("Se elimina producto del inventario por su ID en el Sistema"),
            linkTo(methodOn(InventarioController.class).actualizarInventario(i.getIdinventario(),i)).withRel("Se actualiza producto del inventario por su ID en el Sistema")
        );
    }


}
