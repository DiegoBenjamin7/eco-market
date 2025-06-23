package com.padima.microservicioorden.DTO;


import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representa la Entidad OrdenUsuario")
public class OrdenUsuarioDTO {

    @Schema(description = "Codigo del ID de la Orden", example = "001")
    private Long idorden;

    @Schema(description = "Rut de Usuario", example = "27892745-4")
    private String runusuario;

    @Schema(description = "Cantidad de Productos", example = "7")
    private String productos;

    @Schema(description = "Monto total en pesos CLP", example = "14990")
    private Integer total;

    @Schema(description = "La fecha de la Orden", example = "2024-08-06")
    private LocalDate fechaOrden;

    @Schema(description = "Nombre del Usuario", example = "Diego")
    private String nombre;

    @Schema(description = "Apellido del Usuario", example = "Sepulveda")
    private String apellido;

    @Schema(description = "Contacto de la Persona", example = "9 87656785")
    private Long contacto;


}
