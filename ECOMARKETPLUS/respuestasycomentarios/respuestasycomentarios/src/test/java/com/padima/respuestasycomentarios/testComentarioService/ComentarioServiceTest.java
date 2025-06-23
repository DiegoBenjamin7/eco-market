package com.padima.respuestasycomentarios.testComentarioService;

import com.padima.respuestasycomentarios.exception.ComentarioNotFoundException;
import com.padima.respuestasycomentarios.model.Comentario;
import com.padima.respuestasycomentarios.repository.ComentarioRepository;
import com.padima.respuestasycomentarios.service.ComentarioService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ComentarioServiceTest {
    @Mock
    private ComentarioRepository comentarioRepository;

    @InjectMocks
    private ComentarioService comentarioService;

    @Test
    public void testCrearComentario() {
        Comentario comentario = new Comentario();
        comentario.setContenido("Este es un comentario de prueba");

        when(comentarioRepository.save(comentario)).thenReturn(comentario);

        Comentario resultado = comentarioService.crearComentario(comentario);

        assertEquals("ACTIVO", resultado.getEstado());
        verify(comentarioRepository, times(1)).save(comentario);
    }

    @Test
    public void testObtenerPorIdNotFound() {
        when(comentarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ComentarioNotFoundException.class, () -> {
            comentarioService.obtenerPorId(1L);
        });
    }
}
