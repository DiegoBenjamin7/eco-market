package com.padima.microservicioproducto.model;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="PRODUCTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @Schema(description = "Codigo del producto autogenerado", accessMode = Schema.AccessMode.READ_ONLY)
    private Long idProducto;


    @Column(nullable = false)
    @Schema(description = "Nombre del producto", example = "Botellas de agua reutilizables")
    private String nombreProducto;

    @Column(nullable = false)
    @Schema(description = "Categoría del producto", example = "Ecologico")
    private String categoria;

    @Column(nullable = false)
    @Schema(description = "Cantidad disponible en Stock", example = "100")
    private Integer stock;


    @Column(nullable = false)
    @Schema(description = "Precio unitario del producto", example = "7000")
    private Integer precio;

    
    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Schema(description = "Fecha de caducidad del producto en formato dd-MM-yyyy", example = "2028-11-06")
    private LocalDate fechaCaducidad;


}
