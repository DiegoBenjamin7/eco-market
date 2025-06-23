package com.padima.microservicioproducto.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.padima.microservicioproducto.assembler.ProductoModelAssembler;
import com.padima.microservicioproducto.model.Producto;
import com.padima.microservicioproducto.service.ProductoService;

@RestController
@RequestMapping("/api/ecomarket/productos")
@Tag(
    name = "PRODUCTO",
    description = "Endpoints para gestionar productos: registrar, listar, actualizar, eliminar y consultar información de productos disponibles."
)
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoModelAssembler assembler;


    // ENPOINT QUE LISTA TODOS LOS PRODUCTOS
    @GetMapping
    @Operation(summary = "Listar todo los Productos", description = "Devuelve una lista completa de todas los productos registrados en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se listaron correctamente todos los Productos",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "404", description = "No se encontró ningun Producto",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "No se encuentran datos"))),
        @ApiResponse(responseCode = "500", description = "Error al listar Productos")
    })
    public ResponseEntity<?> listarProductos(){
        List<Producto> productos = productoService.buscarTodo();
        if (productos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se puede encontrar Datos");
        } else {
            return ResponseEntity.ok(productos);
        }
    }


    // ENPOINT QUE BUSCA PRODUCTO POR SU ID
    @GetMapping("/{idProducto}")
    @Operation(summary = "Obtener Producto por ID", description = "Recupera la información de un Producto específica mediante su identificador único (id)." )
    @Parameters(value = {
        @Parameter(name = "idProducto", description = "ID del Producto que se requiere buscar", in = ParameterIn.PATH, required = true)
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos para actualizar Producto",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado",
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Error del servidor al actualizar Producto",
            content = @Content)
    })
    public ResponseEntity<?> buscarProducto(@PathVariable Long idProducto){
        try {
            Producto productoBuscado = productoService.buscar(idProducto);
            return ResponseEntity.ok(assembler.toModel(productoBuscado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto NO encontrado XD");
        }
    }


    // ENPOINT QUE GUARDAR PRODUCTO
    @PostMapping
    @Operation(summary = "Guardar nuevo Producto", description = "Registra un nuevo Producto en el sistema a partir de los datos enviados en el cuerpo de la solicitud.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description= "Objeto Producto que va a registrar", required = true, content = @Content(schema = @Schema(implementation = Producto.class))))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos para crear Producto"),
        @ApiResponse(responseCode = "500", description = "Error al crear Producto")
    })
    public ResponseEntity<?> guardarProducto(@RequestBody Producto productoGuardar){
        try {
            Producto productoRegistrar = productoService.guardar(productoGuardar);
            return ResponseEntity.ok(assembler.toModel(productoRegistrar));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("NO SE PUDO GUARDAR PRODUCTO  XD");
        }
    }



    // ENPOINT QUE ACTUALIZA UN PRODUCTO POR ID
    @PutMapping("/{idProducto}")
    @Operation(summary = "Actualizar Producto por ID", description = "Modifica los datos de un Producto existente, identificada por su ID. Requiere el cuerpo del Producto actualizado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos para actualizar el Producto",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "Producto no encontrada",
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Error del servidor al actualizar Producto",
            content = @Content)
    })
    public ResponseEntity<?> actualizarProducto(@PathVariable Long idProducto, @RequestBody Producto productoActualizar){
        try {
            Producto productoBuscado = productoService.buscar(idProducto);
            productoBuscado.setIdProducto(idProducto);
            productoBuscado.setNombreProducto(productoActualizar.getNombreProducto());
            productoBuscado.setCategoria(productoActualizar.getCategoria());
            productoBuscado.setStock(productoActualizar.getStock());
            productoBuscado.setPrecio(productoActualizar.getPrecio());
            productoBuscado.setFechaCaducidad(productoActualizar.getFechaCaducidad());
            productoService.guardar(productoBuscado);

            return ResponseEntity.ok(assembler.toModel(productoBuscado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EL ID DEL PRODUCTO NO ESTÁ REGISTRADO EN LA BASE DE DATO PARA PODER ACTUALIZAR");
        }
    }


    // ENPOINT QUE ELIMINA UN PRODUCTO POR ID
    @DeleteMapping("/{idProducto}")
    @Operation(summary = "Eliminar Producto por ID", description = "Elimina un Producto registrado del Sistema, usando su ID como referencia. Esta acción es irreversible.")
    @Parameters(value = {
        @Parameter(name = "idProducto", description = "Ingrese el ID del Producto a Eliminar", in = ParameterIn.PATH, required = true)
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto eliminado correctamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(type = "string", example = "Se elimina Producto"))),

        @ApiResponse(responseCode = "404", description = "Producto no encontrado",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Producto no registrado"))),
        @ApiResponse(responseCode = "500", description = "Error al eliminar el Producto")
    })
    public ResponseEntity<String> eliminarProducto(@PathVariable Long idProducto){
        try {
            //Producto productoBuscado = productoService.buscarUnProducto(idProducto);
            productoService.eliminar(idProducto);
            return ResponseEntity.status(HttpStatus.OK).body("SE ELIMINO PRODUCTO");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EL PRODUCTO NO ESTA REGISTRADO PARA ELIMINAR");
        }
    }

}
