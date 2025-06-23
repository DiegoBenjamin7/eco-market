package com.padima.microservicioinventario.controller;

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

import com.padima.microservicioinventario.DTO.ProductoDTO;
import com.padima.microservicioinventario.assembler.InventarioModelAssembler;
import com.padima.microservicioinventario.DTO.InventarioProductoDTO;
import com.padima.microservicioinventario.model.Inventario;
import com.padima.microservicioinventario.service.InventarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/ecomarket/inventarios")
@Tag(name = "INVENTARIO", description = "Endpoints para gestionar Inventario: registrar, listar, actualizar, eliminar.")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private InventarioModelAssembler assembler;

    @GetMapping //lista inventario
    @Operation(summary = "Listar todo el inventario", description = "Devuelve una lista completa de todas los productos del inventario registrados en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se listaron correctamente todos los productos del Inventario", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Inventario.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró ningun producto", content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "No se encuentran datos"))),
            @ApiResponse(responseCode = "500", description = "Error al listar Inventario")
    })
    public ResponseEntity<?> listarInventarios() {
        List<Inventario> inventarios = inventarioService.buscarTodo();
        if (inventarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentran productos en el inventario en la BD");
        } else {
            return ResponseEntity.ok(inventarios);
        }
    }

    // ENPOINT QUE BUSCA INVENTARIO POR SU ID
    @GetMapping("/{idinvent}")
    @Operation(summary = "Obtener producto del Inventario por ID", description = "Recupera la información de un producto del Inventario específico mediante su identificador único (id).")
    @Parameters(value = {
            @Parameter(name = "idinvent", description = "ID del Inventario que se requiere buscar", in = ParameterIn.PATH, required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventario encontrado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Inventario.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos al encontrar Inventario", content = @Content),
            @ApiResponse(responseCode = "404", description = "Inventario no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error del servidor.", content = @Content)
    })
    public ResponseEntity<?> buscarInventario(@PathVariable Long idinvent) {
        try {
            Inventario inventario = inventarioService.buscar(idinvent);
            return ResponseEntity.ok(assembler.toModel(inventario));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro INVENTARIO con el ID Buscado");
        }
    }

    // ENPOINT QUE GUARDA INVENTARIO
    @PostMapping
    @Operation(summary = "Guardar nuevo Inventario", description = "Registra un nuevo Inventario en el sistema a partir de los datos enviados en el cuerpo de la solicitud.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto Inventario que va a registrar", required = true, content = @Content(schema = @Schema(implementation = Inventario.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inventario creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Inventario.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos para crear Inventario"),
            @ApiResponse(responseCode = "500", description = "Error al crear Inventario")
    })
    public ResponseEntity<?> guadarInventario(@RequestBody Inventario inventario) {
        try {
            Inventario nuevaInvent = inventarioService.guardar(inventario);
            return ResponseEntity.ok(assembler.toModel(nuevaInvent));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se pudo registrar INVENTARIO XD");
        }
    }

    // ENPOINT QUE ELIMINA UN INVENTARIO POR ID
    @DeleteMapping("/{idinvent}")
    @Operation(summary = "Eliminar Inventario por ID", description = "Elimina un Inventario registrado en el Sistema, usando su ID como referencia. Esta acción es irreversible.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Inventario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error al eliminar el Inventario")
    })
    public ResponseEntity<String> eliminarInventario(@PathVariable Long idinvent) {
        try {
            inventarioService.eliminar(idinvent);
            return ResponseEntity.status(HttpStatus.OK).body("SE PUDO ELIMINAR EL INVENTARIO");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("EL INVENTARIO NO ESTA REGISTRADO PARA PODER SER ELIMINADA");
        }
    }

    // ENPOINT QUE ACTUALIZA UN INVENTARIO POR ID
    @PutMapping("/{idinvent}")
    @Operation(summary = "Actualizar Inventario por ID", description = "Modifica los datos de un Inventario existente, identificada por su ID. Requiere el cuerpo del Inventario actualizado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventario actualizado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Inventario.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos para actualizar el Inventario", content = @Content),
            @ApiResponse(responseCode = "404", description = "Inventario no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error del servidor al actualizar Inventario", content = @Content)
    })
    public ResponseEntity<?> actualizarInventario(@PathVariable Long idinvent,
            @RequestBody Inventario inventarioActualizar) {
        try {
            Inventario inventarioBuscada = inventarioService.buscar(idinvent);
            inventarioBuscada.setIdProducto(inventarioActualizar.getIdProducto());
            inventarioBuscada.setStockActual(inventarioActualizar.getStockActual());
            inventarioBuscada.setUbicacionBodega(inventarioActualizar.getUbicacionBodega());
            inventarioService.guardar(inventarioBuscada);

            return ResponseEntity.ok(assembler.toModel(inventarioBuscada));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("EL ID del INVENTARIO NO ESTÁ REGISTRADO EN LA BD PARA PODER ACTUALIZAR");
        }

    }

    @GetMapping("/InventarioProducto/{idinvent}")
    @Operation(summary = "Obtener Producto con datos de Inventario", description = "Recupera un producto y muestra información adicional del producto asociado a dicho inventario, mediante su ID.")
    public ResponseEntity<?> DatosInventarioProducto(@PathVariable Long idinvent) {
        try {
            Inventario inventarioBuscado = inventarioService.buscar(idinvent);
            ProductoDTO productoDTO = inventarioService.BuscarProducto(inventarioBuscado.getIdProducto());
            InventarioProductoDTO inventarioProductoDTO = new InventarioProductoDTO();

            inventarioProductoDTO.setIdinventario(inventarioBuscado.getIdinventario());
            inventarioProductoDTO.setIdProducto(productoDTO.getIdProducto());
            inventarioProductoDTO.setStock(inventarioBuscado.getStockActual());
            inventarioProductoDTO.setUbicacionBodega(inventarioBuscado.getUbicacionBodega());

            inventarioProductoDTO.setNombreProducto(productoDTO.getNombreProducto());
            inventarioProductoDTO.setCategoria(productoDTO.getCategoria());
            inventarioProductoDTO.setStock(productoDTO.getStock());
            inventarioProductoDTO.setPrecio(productoDTO.getPrecio());
            inventarioProductoDTO.setFechaCaducidad(productoDTO.getFechaCaducidad());

            return ResponseEntity.ok(inventarioProductoDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encuentra el INVENTARIO o el PRODUCTO no està registrado");
        }
    }

}
