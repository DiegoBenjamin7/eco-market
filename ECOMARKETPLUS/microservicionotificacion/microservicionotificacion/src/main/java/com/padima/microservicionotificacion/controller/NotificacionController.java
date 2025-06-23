package com.padima.microservicionotificacion.controller;

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

import com.padima.microservicionotificacion.assembler.NotificacionModelAssembler;
import com.padima.microservicionotificacion.dto.NotificacionUsuarioDto;
import com.padima.microservicionotificacion.dto.UsuarioDto;
import com.padima.microservicionotificacion.model.Notificacion;
import com.padima.microservicionotificacion.service.NotificacionService;

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
@RequestMapping("/api/ecomarket/notificaciones")
@Tag(
    name = "NOTIFICACIÓN",
    description = "Endpoints para gestionar notificaciones: crear, listar, actualizar, eliminar y obtener detalles de Notificaciones con datos por su id.")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private NotificacionModelAssembler assembler;



    // ENPOINT QUE LISTA TODAS LAS NOTIFICACIONES
    @GetMapping
    @Operation(summary = "Lista todas las Notificaciones", description = "Devuelve una lista completa de todas las Notificaciones registradas en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se listaron correctamente todas las Notificaciones",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Notificacion.class))),
        @ApiResponse(responseCode = "404", description = "No se encontró ninguna Notificacion",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "No se encuentran datos"))),
        @ApiResponse(responseCode = "500", description = "Error al listar las Notificaciones")
    })
    public ResponseEntity<?> listarNotifaciones(){
        List<Notificacion> notificacions = notificacionService.buscarTodo();
        if(notificacions.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(notificacions);
    }



    // ENDPOINT QUE  BUSCA UNA VENTA POR ID
    @GetMapping("/{idNotificacion}")
    @Operation(summary = "Obtener Notificación por ID", description = "Recupera la información de una Notificación específica mediante su identificador único (id)." )
    @Parameters(value = {
        @Parameter(name = "idNotificacion", description = "ID de la Notificacion que se requiere buscar", in = ParameterIn.PATH, required = true)
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificaion encontrada correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Notificacion.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos para encontrar la Notificacion",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "Notificacion no encontrada",
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Error del servidor",
            content = @Content)
    })
    public ResponseEntity<?> buscarNotificacion(@PathVariable Long idNotificacion){
        try{
            Notificacion notificacion = notificacionService.buscar(idNotificacion);
            return ResponseEntity.ok(assembler.toModel(notificacion));
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }



    // ENPOINT QUE GUARDAR NOFICACION
    @PostMapping
    @Operation(summary = "Guardar nueva Notificacion", description = "Registra una nueva Notificacion en el sistema a partir de los datos enviados en el cuerpo de la solicitud.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description= "Objeto Notificacion que va a registrar", required = true, content = @Content(schema = @Schema(implementation = Notificacion.class))))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Notificacion creada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Notificacion.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos para crear la Notificacion"),
        @ApiResponse(responseCode = "500", description = "Error al crear la Notificacion")
    })
    public ResponseEntity<?> guardarNotificacion(@RequestBody Notificacion notificacion){
        try {
            Notificacion nuevaNotificacion = notificacionService.guardar(notificacion);
            return ResponseEntity.ok(assembler.toModel(nuevaNotificacion));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se pudo registrar la Nueva Notificacion");
        }
    }




    // ENPOINT QUE ACTUALIZA LA NOTIFICACION POR ID
    @PutMapping("/{idNotificacion}")
    @Operation(summary = "Actualizar Notificación por ID", description = "Modifica los datos de una Notificacion existente, identificada por su ID. Requiere el cuerpo de la Notificacion.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificacion actualizada correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Notificacion.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos para actualizar la Notificacion",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "Notificacion no encontrada",
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Error del servidor al actualizar Notificacion",
            content = @Content)
    })
    public ResponseEntity<?> actualizarNotificacion(@PathVariable Long idNotificacion, @RequestBody Notificacion notificacionActualizar){
        try{
            Notificacion notificacionBuscado = notificacionService.buscar(idNotificacion);
            notificacionBuscado.setId(idNotificacion);
            notificacionBuscado.setRun(notificacionActualizar.getRun());
            notificacionBuscado.setMensaje(notificacionActualizar.getMensaje());
            notificacionBuscado.setFechaEnvio(notificacionActualizar.getFechaEnvio());
            notificacionBuscado.setEstado(notificacionActualizar.getEstado());
            notificacionService.guardar(notificacionBuscado);

            return ResponseEntity.ok(assembler.toModel(notificacionBuscado));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EL ID DE LA NOTIFICACIÓN NO ESTÁ REGISTRADO EN LA BASE DE DATO PARA PODER ACTUALIZAR");
        }
        
    }



    // ENPOINT QUE ELIMINA UNA NOTIFICACION POR ID
    @DeleteMapping("/{idNotifacion}")
    @Operation(summary = "Eliminar Notificación por ID", description = "Elimina una Notificacion registrada en el Sistema, usando su ID como referencia. Esta acción es irreversible.")
    @Parameters(value = {
        @Parameter(name = "idNotifacion", description = "Ingrese el ID de la Notificacion a Eliminar", in = ParameterIn.PATH, required = true)
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificacion eliminada correctamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(type = "string", example = "Se elimina Notificacion"))),

        @ApiResponse(responseCode = "404", description = "Notificacion no encontrada",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Notificacion no eliminada"))),
        @ApiResponse(responseCode = "500", description = "Error al eliminar la Notificacion")
    })
    public ResponseEntity<?> eliminarNotificacion(@PathVariable long idNotifacion){
        try{
            notificacionService.eliminar(idNotifacion);
            return ResponseEntity.status(HttpStatus.OK).body("SE ELIMINÓ LA NOTIFICION EXITOSAMENTE");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("LA NOTIFICACION NO ESTA REGISTRADO PARA PODER SER ELIMINADO");
        }
    }

    @GetMapping("/NotificacionUsuario/{id}")
    public ResponseEntity<?> DatosNotificacionUsuario(@PathVariable Long id){
        try {
            Notificacion notificacionBuscada = notificacionService.buscar(id);
            UsuarioDto usuarioDTO = notificacionService.BuscarUsuario(notificacionBuscada.getRun());
            NotificacionUsuarioDto notificacionUsuarioDTO = new NotificacionUsuarioDto();


            notificacionUsuarioDTO.setId(notificacionBuscada.getId());
            notificacionUsuarioDTO.setRun(notificacionBuscada.getRun());
            notificacionUsuarioDTO.setMensaje(notificacionBuscada.getMensaje());
            notificacionUsuarioDTO.setFechaEnvio(notificacionBuscada.getFechaEnvio());
            notificacionUsuarioDTO.setEstado(notificacionBuscada.getEstado());


            notificacionUsuarioDTO.setNombre(usuarioDTO.getNombre());
            notificacionUsuarioDTO.setApellido(usuarioDTO.getApellido());
            notificacionUsuarioDTO.setDireccion(usuarioDTO.getDireccion());
            notificacionUsuarioDTO.setContacto(usuarioDTO.getContacto());
            notificacionUsuarioDTO.setEmail(usuarioDTO.getEmail());
          

            return ResponseEntity.ok(notificacionUsuarioDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra registrado el ID de la NOTIFICACION");
        }
    }

}

