package com.padima.microservicionotificacion.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name="NOTIFICACION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @Schema(description = "Codigo de la Notificacion autogenerado", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;


    @Column(unique = true, length = 13, nullable = false)
    @Schema(description = "El Rut de usuario", example = "27020812-4")
    private String run;


    @Column(nullable = false)
    @Schema(description = "Mensaje", example = "Tu producto será entregado hoy.")
    private String mensaje;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Schema(description = "La fecha de la Notificacion", example = "2024-02-01")
    private LocalDate fechaEnvio;


    @Column(nullable = false)
    @Schema(description = "Estado de la notificacion", example = "Enviado")
    private String estado;


}
