package com.padima.microserviciologin.controller;

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

import com.padima.microserviciologin.assembler.LoginModelAssembler;
import com.padima.microserviciologin.model.Login;
import com.padima.microserviciologin.service.LoginService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/ecomarket/logins")
@Tag(
    name = "Login o Sesión",
    description = "Endpoints para gestionar sesiones de usuario: iniciar sesión, cerrar sesión, validar credenciales y registrar accesos.")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private LoginModelAssembler assembler;


    // ENPOINT QUE LISTA TODOS LOS LOGIN
    @GetMapping
    @Operation(summary = "Listar Logins o sesiones", description = "Valida las credenciales del usuario y registra el inicio de sesión en el sistema.")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Login.class))),
    @ApiResponse(responseCode = "404", description = "Credenciales inválidas",
        content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "Usuario o contraseña incorrectos"))),
    @ApiResponse(responseCode = "500", description = "Error interno al procesar la solicitud")
    })
    public ResponseEntity<?> listarLogins(){
        List<Login> logins = loginService.buscarTodo();
        if(logins.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(logins);
    }


    // ENPINT QUE  BUSCA UN LOING POR ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener sesión por ID", description = "Recupera la información de una sesión específica mediante su id." )
    @Parameters(value = {
        @Parameter(name = "id", description = "ID de la sesión que se desea consultar", in = ParameterIn.PATH, required = true)
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sesión encontrada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Login.class))),
        @ApiResponse(responseCode = "400", description = "ID de sesión inválido",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "Sesión no encontrada",
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Error del servidor al recuperar la sesión",
            content = @Content)
    })
    public ResponseEntity<?> buscarLogin(@PathVariable Long id){
        try{
            Login login = loginService.buscar(id);
            return ResponseEntity.ok(assembler.toModel(login));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro Login con el ID");
        }
    }

    // ENPOINT QUE GUARDA SESIÓN (INICIAR SESIÓN)
    @PostMapping
    @Operation(summary = "Registrar nueva sesión", description = "Registra un nuevo inicio de sesión en el sistema.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description= "Objeto Login con los datos de inicio de sesión", required = true, content = @Content(schema = @Schema(implementation = Login.class))))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Sesión iniciada correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Login.class))),
        @ApiResponse(responseCode = "400", description = "Credenciales inválidas para iniciar sesión"),
        @ApiResponse(responseCode = "500", description = "Error interno al registrar la sesión")
    })
    public ResponseEntity<?> guardarLogin(@RequestBody Login login){
        try {
            Login nuevoLogin = loginService.guardar(login);
            return ResponseEntity.ok(assembler.toModel(nuevoLogin));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se pudo registrar el nuevo Login");
        }
    }


    // ENPOINT QUE ACTUALIZA SESIÓN POR ID
    @Operation(
    summary = "Actualizar sesión por ID",
    description = "Modifica los datos de una sesión existente, identificada por su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sesión actualizada correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Login.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos para actualizar la sesión",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "Sesión no encontrada",
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Error del servidor al actualizar la sesión",
            content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarLogin(@PathVariable Long id, @RequestBody Login loginActualizar){
        try{
            Login loginBuscado = loginService.buscar(id);
            loginBuscado.setId(id);
            loginBuscado.setEmailUsuario(loginActualizar.getEmailUsuario());
            loginBuscado.setPassword(loginActualizar.getPassword());
            loginBuscado.setEstadoSesion(loginActualizar.getEstadoSesion());
            loginBuscado.setFechaInicio(loginActualizar.getFechaInicio());
            loginBuscado.setFechaTermino(loginActualizar.getFechaTermino());
            
            loginService.guardar(loginBuscado);
            return ResponseEntity.ok(loginActualizar);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EL ID DEL LOGIN NO ESTÁ REGISTRADO EN LA BASE DE DATO PARA PODER SER ACTUALIZADA");
        }
        
    }


    // ENPOINT QUE  ELIMINA LOGIN POR ID
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar sesión por ID",
        description = "Elimina una sesión registrada en el sistema utilizando su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sesión eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Sesión no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error al eliminar la sesión")
    })
    public ResponseEntity<?> eliminarLogin(@PathVariable long id){
        try{
            loginService.eliminar(id);
            return ResponseEntity.status(HttpStatus.OK).body("SE ELIMINÓ EL USUARIO");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ESTE LOGIN NO PUEDE SER ELIMINADO YA QUE NO EXISTE");
        }
    }

}
