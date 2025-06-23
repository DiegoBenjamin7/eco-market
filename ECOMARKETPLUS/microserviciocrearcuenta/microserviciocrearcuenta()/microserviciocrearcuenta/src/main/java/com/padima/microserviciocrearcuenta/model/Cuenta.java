package com.padima.microserviciocrearcuenta.model;

import java.time.Instant;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="CUENTA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 100)
    private String nombreCompleto;

    @Column(nullable = false, length = 20)
    private String estado; // Ejemplo: ACTIVA, INACTIVA, BLOQUEADA

    @Column(nullable = false)
    private Instant fechaCreacion;

    @Column
    private Instant fechaActualizacion;
}