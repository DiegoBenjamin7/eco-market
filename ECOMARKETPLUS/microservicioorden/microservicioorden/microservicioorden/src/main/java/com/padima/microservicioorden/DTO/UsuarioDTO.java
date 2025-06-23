package com.padima.microservicioorden.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private Long run;
    private String nombre;
    private String apellido;
    private Long contacto;


}
