package com.padima.microservicioproveedores.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="PROVEEDORES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class proveedores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 9, unique = true)
    private String rutEmpresa; 

    @Column(length = 100)
    private String direccion;

    @Column(length = 100)
    private String email;

    @Column(length = 11)
    private String telefono;

    @Column(nullable = false, length = 50)
    private String tipo; // "alimentos", "TECNOLOGIA", "ETC"
}