package com.padima.microservicioproducto.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.padima.microservicioproducto.controller.ProductoController;
import com.padima.microservicioproducto.model.Producto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProductoModelAssembler  implements RepresentationModelAssembler <Producto, EntityModel<Producto>>{

    @Override
    public EntityModel<Producto> toModel(Producto p){
        return EntityModel.of(
            p,
            linkTo(methodOn(ProductoController.class).buscarProducto(p.getIdProducto())).withRel("Se busca PRODUCTO por su ID en el Sistema "),
            linkTo(methodOn(ProductoController.class).listarProductos()).withRel("Se lista todos los PRODUCTOS en el Sistema"),
            linkTo(methodOn(ProductoController.class).eliminarProducto(p.getIdProducto())).withRel("Se elimina PRODUCTO por su ID en el Sistema"),
            linkTo(methodOn(ProductoController.class).actualizarProducto(p.getIdProducto(),p)).withRel("Se actualiza PRODUCTO por su ID en el Sistema")
            );
    }


}
