package com.padima.microserviciopago.assembler;


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.padima.microserviciopago.controller.PagoController;
import com.padima.microserviciopago.model.Pago;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PagoModelAssembler  implements RepresentationModelAssembler <Pago, EntityModel<Pago>>{

    @Override
    public EntityModel<Pago> toModel(Pago p){
        return EntityModel.of(
            p,
            linkTo(methodOn(PagoController.class).buscarPago(p.getIdpago())).withRel("Se busca PAGO por su ID en el Sistema "),
            linkTo(methodOn(PagoController.class).listarPago()).withRel("Se lista todos los PAGOS en el Sistema"),
            linkTo(methodOn(PagoController.class).eliminarPago(p.getIdpago())).withRel("Se elimina PAGO por su ID en el Sistema"),
            linkTo(methodOn(PagoController.class).actualizarPago(p.getIdpago(),p)).withRel("Se actualiza PAGO por su ID en el Sistema")
        );
    }


}
