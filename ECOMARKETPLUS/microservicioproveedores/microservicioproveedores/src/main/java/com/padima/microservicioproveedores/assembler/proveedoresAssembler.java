package com.padima.microservicioproveedores.assembler;

import com.padima.microservicioproveedores.controller.proveedoresController;
import com.padima.microservicioproveedores.model.proveedores;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class proveedoresAssembler implements RepresentationModelAssembler<proveedores, EntityModel<proveedores>> {

    @Override
    public EntityModel<proveedores> toModel(proveedores proveedor) {
        return EntityModel.of(proveedor,
                // Self link
                linkTo(methodOn(proveedoresController.class)
                    .buscarProveedorPorId(proveedor.getId())).withSelfRel(),
                
                // Collection link
                linkTo(methodOn(proveedoresController.class)
                    .listarProveedores(null, null)).withRel("proveedores"),
                
                // Create link
                linkTo(methodOn(proveedoresController.class)
                    .crearProveedor(null)).withRel("create"),
                
                // Update link
                linkTo(methodOn(proveedoresController.class)
                    .actualizarProveedor(proveedor.getId(), null)).withRel("update"),
                
                // Delete link
                linkTo(methodOn(proveedoresController.class)
                    .eliminarProveedor(proveedor.getId())).withRel("delete"),
                
                // Filter by type link
                linkTo(methodOn(proveedoresController.class)
                    .buscarPorTipo(proveedor.getTipo())).withRel("filterByType"),
                
                // Related resources (assuming relationships exist)
                linkTo(methodOn(proveedoresController.class)
                    .buscarProveedorPorId(proveedor.getId())).withRel("productos"),
                linkTo(methodOn(proveedoresController.class)
                    .buscarProveedorPorId(proveedor.getId())).withRel("contactos")
        );
    }
}
