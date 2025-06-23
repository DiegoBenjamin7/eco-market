package com.padima.microservicionotificacion.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {

    private Long run;
    private String nombre;
    private String apellido;
    private String direccion;
    private Long contacto;
    private String email;

}
