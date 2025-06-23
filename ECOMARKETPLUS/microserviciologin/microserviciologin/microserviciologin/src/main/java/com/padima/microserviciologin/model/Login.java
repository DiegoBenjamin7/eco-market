package com.padima.microserviciologin.model;

import java.time.Instant;


import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name="LOGIN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login {


    @Id
    @Column(name = "ID")
    @Schema(description = "Codigo de la venta autogenerado", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;


    @Column(nullable = false)
    @Schema(description = "El Email del usuario", example = "diego@duocuc.cl")
    private String EmailUsuario;


    @Column(nullable = false)
    @Schema(description = "El Password del usuario", example = "Padima77777")
    private String password;

    @Column(nullable = false)
    @Schema(description = "El Estado se Sesion", example = "activo, inactivo")
    private String estadoSesion ;

    @Column(nullable = false)
    @Schema(description = "Fecha de inicio de sesion", example = "2022-07-01")
    private Instant fechaInicio;

    @Column(nullable = false)
    @Schema(description = "Fecha de Termino de sesion", example = "2029-01-05")
    private Instant fechaTermino;

}
