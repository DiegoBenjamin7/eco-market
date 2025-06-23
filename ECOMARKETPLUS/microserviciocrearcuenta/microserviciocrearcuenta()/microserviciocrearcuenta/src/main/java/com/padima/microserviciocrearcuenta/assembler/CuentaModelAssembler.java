package com.padima.microserviciocrearcuenta.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import com.padima.microserviciocrearcuenta.controller.CuentaController;
import com.padima.microserviciocrearcuenta.model.Cuenta;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CuentaModelAssembler implements RepresentationModelAssembler<Cuenta, EntityModel<Cuenta>> {
    @Override
    public EntityModel<Cuenta> toModel(Cuenta cuenta) {
        return EntityModel.of(cuenta,
            linkTo(methodOn(CuentaController.class).buscarCuenta(cuenta.getId())).withRel("detalle"),
            linkTo(methodOn(CuentaController.class).listarCuentas()).withRel("lista"),
            linkTo(methodOn(CuentaController.class).eliminarCuenta(cuenta.getId())).withRel("eliminar"),
            linkTo(methodOn(CuentaController.class).actualizarCuenta(cuenta.getId(), cuenta)).withRel("actualizar")
        );
    }
}
