package com.padima.respuestasycomentarios.service;

import com.padima.respuestasycomentarios.exception.ComentarioNotFoundException;
import com.padima.respuestasycomentarios.model.Comentario;
import com.padima.respuestasycomentarios.repository.ComentarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComentarioService {
    private final ComentarioRepository comentarioRepository;

    // CREATE
    public Comentario crearComentario(Comentario comentario) {
        comentario.setEstado("ACTIVO");
        comentario.setLikes(0);
        return comentarioRepository.save(comentario);
    }

    // READ (all)
    public List<Comentario> obtenerTodos() {
        return comentarioRepository.findAll();
    }

    // READ (by ID)
    public Comentario obtenerPorId(Long id) {
        return comentarioRepository.findById(id)
            .orElseThrow(() -> new ComentarioNotFoundException("Comentario no encontrado con ID: " + id));
    }

    // READ (by Publicación)
    public List<Comentario> obtenerPorPublicacion(Long publicacionId) {
        return comentarioRepository.findByPublicacionId(publicacionId);
    }

    // UPDATE (contenido)
    public Comentario actualizarComentario(Long id, String nuevoContenido) {
        Comentario comentario = obtenerPorId(id);
        comentario.setContenido(nuevoContenido);
        return comentarioRepository.save(comentario);
    }

    // UPDATE (likes)
    public Comentario darLike(Long id) {
        Comentario comentario = obtenerPorId(id);
        comentario.setLikes(comentario.getLikes() + 1);
        return comentarioRepository.save(comentario);
    }

    // DELETE (soft delete)
    public Comentario eliminarComentario(Long id) {
        Comentario comentario = obtenerPorId(id);
        comentario.setEstado("ELIMINADO");
        return comentarioRepository.save(comentario);
    }
}
