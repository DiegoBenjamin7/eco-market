package com.padima.respuestasycomentarios.repository;

import com.padima.respuestasycomentarios.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByPublicacionId(Long publicacionId);
    List<Comentario> findByUsuarioId(Long usuarioId);
    List<Comentario> findByEstado(String estado);
    boolean existsByIdAndUsuarioId(Long id, Long usuarioId);
}
// Este repositorio extiende JpaRepository para proporcionar operaciones CRUD básicas
// y métodos personalizados para buscar comentarios por publicación, usuario y estado.
