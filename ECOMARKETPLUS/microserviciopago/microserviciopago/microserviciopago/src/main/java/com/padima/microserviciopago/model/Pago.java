package com.padima.microserviciopago.model;


import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "PAGO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representa la Entidad de Pago")
public class Pago {

    @Id
    @Schema(description = "Codigo del Pago autogenerado", accessMode = Schema.AccessMode.READ_ONLY)
    private Long idpago;


    @Column(nullable = false)
    @Schema(description = "ID de la orden asociada al pago", example = "1001")
    private Long ordenId;

    @Column(nullable = false)
    @Schema(description = "Monto total del pago en pesos chilenos", example = "59990")
    private Integer monto;

    @Column(nullable = false)
    @Schema(description = "Método de pago utilizado", example = "Debito-Credito")
    private String metodoPago;

    @Column(nullable = false)
    @Schema(description = "Estado actual del pago", example = "exitoso")
    private String estadoPago;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Schema(description = "Fecha en la que se realizó el pago", example = "2025-08-06")
    private LocalDate fechaPago;

}
