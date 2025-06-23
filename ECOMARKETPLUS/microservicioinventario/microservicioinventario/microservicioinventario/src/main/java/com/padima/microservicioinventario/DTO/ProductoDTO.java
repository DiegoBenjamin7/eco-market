package com.padima.microservicioinventario.DTO;

import java.time.LocalDate;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {

    private Long idProducto;
    private String nombreProducto;
    private String categoria;
    private Integer stock;
    private Integer precio;
    private LocalDate fechaCaducidad;

}
