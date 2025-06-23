package com.padima.microservicioenvio.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ENVIO")
public class Envio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigoEnvio;

    @Column(nullable = false)
    private Long idRuta; // Relación con microservicio de rutas

    @Column(nullable = false)
    private Long idPedido; // Relación con microservicio de pedidos

    @Column(nullable = false)
    private String estado; // "PREPARACION", "EN_TRANSITO", "ENTREGADO"

    @Column(nullable = false)
    private Double pesoKg;

    @Column(nullable = false)
    private String direccionDestino;
}
