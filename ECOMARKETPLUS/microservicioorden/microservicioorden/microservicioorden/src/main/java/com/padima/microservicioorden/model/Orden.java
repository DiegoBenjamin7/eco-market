package com.padima.microservicioorden.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "ORDEN")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representa la entidad Orden")
public class Orden {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDORDEN")
    @Schema(description = "Codigo de la Orden autogenerado", accessMode = Schema.AccessMode.READ_ONLY)
    private Long idorden;

    @Column(nullable = false)
    @Schema(description = "RUN del usuario que realiza la compra", example = "27892745-4")
    private String runusuario;

    @Column(nullable = false)
    @Schema(description = "Listado de productos comprados", example = "[{\"productoId\":1,\"cantidad\":2}]")
    private String productos;

    @Column(nullable = false)
    @Schema(description = "Monto total de la orden", example = "14990")
    private Integer total;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Schema(description = "Fecha en que se realizó la orden", example = "2026-08-06")
    private LocalDate fechaOrden;



}



