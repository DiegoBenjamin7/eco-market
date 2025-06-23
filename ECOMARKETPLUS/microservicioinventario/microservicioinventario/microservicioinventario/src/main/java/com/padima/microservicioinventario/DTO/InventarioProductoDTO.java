package com.padima.microservicioinventario.DTO;


import java.time.LocalDate;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventarioProductoDTO {

    private Long idinventario;
    private Long idProducto;
    private Integer stockActual;
    private String ubicacionBodega;


    private String nombreProducto;
    private String categoria;
    private Integer stock;
    private Integer precio;
    private LocalDate fechaCaducidad;

}
