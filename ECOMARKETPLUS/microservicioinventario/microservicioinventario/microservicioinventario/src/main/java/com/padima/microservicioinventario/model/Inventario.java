package com.padima.microservicioinventario.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="INVENTARIO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {

    @Id
    @Schema(description = "Codigo del Inventario autogenerado", accessMode = Schema.AccessMode.READ_ONLY)
    private Long idinventario;

    @Column(nullable = false)
    @Schema(description = "El id del Producto", example = "25")
    private Integer idProducto;

    @Column(nullable = false)
    @Schema(description = "Stock ", example = "40")
    private Integer stockActual;

    @Column(nullable = false)
    @Schema(description = "Ubicacion de la Bodega", example = "Bodega Serena")
    private String ubicacionBodega;

}
