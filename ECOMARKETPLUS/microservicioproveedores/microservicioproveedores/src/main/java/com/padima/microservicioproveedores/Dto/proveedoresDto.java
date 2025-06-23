package com.padima.microservicioproveedores.Dto;

import lombok.Data;

@Data
public class proveedoresDto {
    private Long id;
    private String nombre;
    private String rutEmpresa;
    private String direccion;
    private String email;
    private String telefono;
    private String tipo;
}