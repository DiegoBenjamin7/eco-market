package com.padima.microservicioorden.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.padima.microservicioorden.DTO.OrdenUsuarioDTO;
import com.padima.microservicioorden.DTO.UsuarioDTO;
import com.padima.microservicioorden.assembler.OrdenModelAssembler;
import com.padima.microservicioorden.model.Orden;
import com.padima.microservicioorden.service.OrdenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/ecomarket/ordenes")
@Tag(
    name = "ORDEN",
    description = "Endpoints para gestionar órdenes de venta: registrar nuevas órdenes, listar todas las órdenes, actualizar estados, eliminar registros y consultar detalles de una orden específica.")
public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    @Autowired
    private OrdenModelAssembler assembler;


    // ENPOINT QUE LISTA TODOS LOS PRODUCTOS
   @GetMapping
   @Operation(summary = "Listar todas las Ordenes", description = "Devuelve una lista completa de todas las Ordenes registrados en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se listaron correctamente todas las Ordenes",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Orden.class))),
        @ApiResponse(responseCode = "404", description = "No se encontró ningun Orden",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "No se encuentran datos"))),
        @ApiResponse(responseCode = "500", description = "Error al listar Orden")
    })
    public ResponseEntity<?> listarOrdenes(){
        List<Orden> ordenes = ordenService.buscarTodo();
        if (ordenes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentran las ORDENES registradas en la BD");
        } else {
            return ResponseEntity.ok(ordenes);
        }
    }


    // ENPOINT QUE BUSCA ORDEN POR SU ID
    @GetMapping("/{idorden}")
    @Operation(summary = "Obtener Orden por ID", description = "Recupera la información de una Orden específica mediante su identificador único (id)." )
    @Parameters(value = {
        @Parameter(name = "idorden", description = "ID de la Orden que se requiere buscar", in = ParameterIn.PATH, required = true)
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orden actualizada correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Orden.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos para actualizar Orden",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "Orden no encontrado",
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Error del servidor al actualizar Orden",
            content = @Content)
    })
    public ResponseEntity<?> buscarOrden(@PathVariable Long idorden){
        try {
            Orden orden = ordenService.buscar(idorden);
            return ResponseEntity.ok(assembler.toModel(orden));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la ORDEN con el ID buscado");
        }
    }


    // ENPOINT QUE GUARDAR ORDEN
    @PostMapping
    @Operation(summary = "Guardar nueva Orden", description = "Registra una nueva Orden en el sistema a partir de los datos enviados en el cuerpo de la solicitud.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description= "Objeto Orden que va a registrar", required = true, content = @Content(schema = @Schema(implementation = Orden.class))))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Orden creado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Orden.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos para crear Orden"),
        @ApiResponse(responseCode = "500", description = "Error al crear Orden")
    })
    public ResponseEntity<?> guadarOrden(@RequestBody Orden orden){
        try {
            Orden nuevaOrden = ordenService.guardar(orden);
            return ResponseEntity.ok(assembler.toModel(nuevaOrden));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se pudo registrar la Nueva Orden");
        }
    }



    // ENPOINT QUE ELIMINA UNA ORDEN POR ID
    @DeleteMapping("/{idorden}")
    @Operation(summary = "Eliminar Orden por ID", description = "Elimina una Orden registrado del Sistema, usando su ID como referencia. Esta acción es irreversible.")
    @Parameters(value = {
        @Parameter(name = "idorden", description = "Ingrese el ID de la Orden a Eliminar", in = ParameterIn.PATH, required = true)
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orden eliminado correctamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(type = "string", example = "Se elimina Orden"))),

        @ApiResponse(responseCode = "404", description = "Orden no encontrado",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Orden no registrado"))),
        @ApiResponse(responseCode = "500", description = "Error al eliminar la Orden")
    })
    public ResponseEntity<String> eliminarOrden(@PathVariable Long idorden){
        try{
            ordenService.eliminar(idorden);
            return ResponseEntity.status(HttpStatus.OK).body("SE ELIMINÓ LA ORDEN EXITOSAMENTE :)");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("LA ORDEN NO ESTA REGISTRADA PARA PODER SER ELIMINADA");
        }
    }



    // ENPOINT QUE ACTUALIZA UNA ORDEN POR ID
    @PutMapping("/{idorden}")
    @Operation(summary = "Actualizar Orden por ID", description = "Modifica los datos de una Orden existente, identificada por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orden actualizado correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Orden.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos para actualizar la Orden",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "Orden no encontrada",
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Error del servidor al actualizar Orden",
            content = @Content)
    })
    public ResponseEntity<?> actualizarOrden(@PathVariable Long idorden, @RequestBody Orden ordenActualizar){
        try{
            Orden ordenBuscada = ordenService.buscar(idorden);
            ordenBuscada.setRunusuario(ordenActualizar.getRunusuario());
            ordenBuscada.setProductos(ordenActualizar.getProductos());
            ordenBuscada.setTotal(ordenActualizar.getTotal());
            ordenBuscada.setFechaOrden(ordenActualizar.getFechaOrden());
            ordenService.guardar(ordenBuscada);

            return ResponseEntity.ok(assembler.toModel(ordenBuscada));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EL ID DE LA ORDEN NO ESTÁ REGISTRADA EN LA BASE DE DATO PARA PODER  SER ACTUALIZADA");
        }
        
    }


    @GetMapping("/OrdenUsuario/{idorden}")
    @Operation(summary = "Obtener una Orden con datos de usuario", description = "Recupera  una Orden  y muestra información adicional del usuario asociado a dicha Orden con su id.")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Orden y datos de usuario obtenidos correctamente",
        content = @Content(mediaType = "Applicación/JSON", schema = @Schema(implementation = OrdenUsuarioDTO.class))),
    @ApiResponse(responseCode = "404", description = "No se encontró la Orden o el usuario",
        content = @Content),
    @ApiResponse(responseCode = "500", description = "Error al obtener los datos")
    })
    public ResponseEntity<?> datosOrdenUsuario(@PathVariable Long idorden){
        try {
            Orden ordenBuscada = ordenService.buscar(idorden);
            UsuarioDTO usuarioDTO = ordenService.BuscarUsuario(ordenBuscada.getRunusuario());
            OrdenUsuarioDTO ordenUsuarioDTO = new OrdenUsuarioDTO();

            
            ordenUsuarioDTO.setIdorden(ordenBuscada.getIdorden());
            ordenUsuarioDTO.setRunusuario(ordenBuscada.getRunusuario());
            ordenUsuarioDTO.setProductos(ordenBuscada.getProductos());
            ordenUsuarioDTO.setTotal(ordenBuscada.getTotal());
            ordenUsuarioDTO.setFechaOrden(ordenBuscada.getFechaOrden());

            ordenUsuarioDTO.setNombre(usuarioDTO.getNombre());
            ordenUsuarioDTO.setApellido(usuarioDTO.getApellido());
            ordenUsuarioDTO.setContacto(usuarioDTO.getContacto());


            return ResponseEntity.ok(ordenUsuarioDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra la ORDEN o USUARIO no esta registrado");
        }
    }

}
