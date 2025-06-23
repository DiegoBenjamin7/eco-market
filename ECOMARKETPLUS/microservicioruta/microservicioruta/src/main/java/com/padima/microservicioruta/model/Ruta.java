package com.padima.microservicioruta.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="RUTA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ruta {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigoRuta;

    @Column(nullable = false)
    private String origen;

    @Column(nullable = false)
    private String destino;

    @Column(nullable = false)
    private Double distanciaKm;

    @Column(nullable = false)
    private Integer tiempoEstimadoMinutos;

    @Column(nullable = false)
    private String tipoTransporte; //tipo de transporte ejemplo, terrestre o si va volando

    @Column(nullable = false)
    private String estado; // ejemplo pendiente, confirmado, o canceladooooooo
}
