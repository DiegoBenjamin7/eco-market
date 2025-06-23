package com.padima.microserviciopago.controller;

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

import com.padima.microserviciopago.assembler.PagoModelAssembler;
import com.padima.microserviciopago.model.Pago;
import com.padima.microserviciopago.service.PagoService;

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
@RequestMapping("/api/perfulandia/pagos")
@Tag(
    name = "PAGO",
    description = "Endpoints para gestionar pagos: registrar, listar, actualizar, eliminar y obtener detalles de los pagos realizados por los usuarios.")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private PagoModelAssembler assembler;


    // ENPOINT QUE LISTA TODOS LOS PAGOS
    @GetMapping
    @Operation(summary = "Listar todo los Pagos", description = "Devuelve una lista completa de todas los pagos registrados en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se listaron correctamente todos los Pagos",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pago.class))),
        @ApiResponse(responseCode = "404", description = "No se encontró ningun Pago",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "No se encuentran datos"))),
        @ApiResponse(responseCode = "500", description = "Error al listar Pago")
    })
    public ResponseEntity<?> listarPago(){
        List<Pago> pagos = pagoService.BuscarTodo();
        if(pagos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pagos);
    }


    // ENPOINT QUE BUSCA PAGO POR SU ID
    @GetMapping("/{idPago}")
    @Operation(summary = "Obtener Pago por ID", description = "Recupera la información de un Pago específica mediante su identificador único (id)." )
    @Parameters(value = {
        @Parameter(name = "idPago", description = "ID del Pago que se requiere buscar", in = ParameterIn.PATH, required = true)
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago actualizado correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pago.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos para actualizar Pago",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado",
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Error del servidor al actualizar Pago",
            content = @Content)
    })
    public ResponseEntity<?> buscarPago(@PathVariable Long idPago){
        try{
            Pago pago = pagoService.Buscar(idPago);
            return ResponseEntity.ok(assembler.toModel(pago));
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }



    // ENPOINT QUE GUARDA PAGO
    @PostMapping
    @Operation(summary = "Guardar nuevo Pago", description = "Registra un nuevo Pago en el sistema a partir de los datos enviados en el cuerpo de la solicitud.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description= "Objeto Pago que va a registrar", required = true, content = @Content(schema = @Schema(implementation = Pago.class))))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pago creado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pago.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos para crear Pago"),
        @ApiResponse(responseCode = "500", description = "Error al crear Pago")
    })
    public ResponseEntity<?> guardarPago(@RequestBody Pago pago){
        try {
            Pago pagoRegistrar = pagoService.Guardar(pago);
            return ResponseEntity.ok(assembler.toModel(pagoRegistrar));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("NO SE PUDO GUARDAR PAGO  XD");
        }
    }


    // ENPOINT QUE ACTUALIZA UN PAGO POR ID
    @PutMapping("/{idPago}")
    @Operation(summary = "Actualizar Pago por ID", description = "Modifica los datos de un Pago existente, identificada por su ID. Requiere el cuerpo del Pago actualizado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago actualizado correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pago.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos para actualizar el Pago",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "Pago no encontrada",
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Error del servidor al actualizar Pago",
            content = @Content)
    })
    public ResponseEntity<?> actualizarPago(@PathVariable Long idPago, @RequestBody Pago pagoActualizar){
        try{
            Pago pagoBuscado = pagoService.Buscar(idPago);
            pagoBuscado.setIdpago(idPago);
            pagoBuscado.setOrdenId(pagoActualizar.getOrdenId());
            pagoBuscado.setMonto(pagoActualizar.getMonto());
            pagoBuscado.setMetodoPago(pagoActualizar.getMetodoPago());
            pagoBuscado.setFechaPago(pagoActualizar.getFechaPago());
            pagoService.Guardar(pagoBuscado);

            return ResponseEntity.ok(assembler.toModel(pagoBuscado));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EL ID DEL PAGO NO ESTÁ REGISTRADO EN LA BASE DE DATO PARA PODER ACTUALIZAR");
        }
        
    }


    // ENPOINT QUE ELIMINA UN PAGO POR ID
    @Operation(summary = "Eliminar Pago por ID", description = "Elimina un Pago registrado en el Sistema, usando su ID como referencia. Esta acción es irreversible.")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Pago eliminado correctamente"),
    @ApiResponse(responseCode = "404", description = "Pago no encontrado"),
    @ApiResponse(responseCode = "500", description = "Error al eliminar el Pago")
    })
    @DeleteMapping("/{idPago}")
    public ResponseEntity<?> eliminarPago(@PathVariable long idPago){
        try{
            pagoService.Eliminar(idPago);
            return ResponseEntity.status(HttpStatus.OK).body("¡SE ELIMINÓ EL PAGO CON ÉXITO!");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EL PAGO NO ESTA REGISTRADO PARA PODER SER ELIMINADO");
        }
    }

}
