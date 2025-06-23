package com.padima.respuestasycomentarios.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "COMENTARIOS")
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long usuarioId; // ID del usuario que hizo el comentario

    @Column(nullable = false)
    private String contenido;

    @Column(nullable = false)
    private String estado; // "ACTIVO", "ELIMINADO", "OCULTO"

    @Column(nullable = false)
    private Long publicacionId; // ID de la publicación relacionada

    @Column(nullable = false)
    private Integer likes = 0;
}
