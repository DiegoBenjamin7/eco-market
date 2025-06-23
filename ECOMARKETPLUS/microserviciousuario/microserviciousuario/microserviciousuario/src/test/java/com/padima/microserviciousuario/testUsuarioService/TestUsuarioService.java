package com.padima.microserviciousuario.testUsuarioService; 

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.padima.microserviciousuario.repository.UsuarioRepository;
import com.padima.microserviciousuario.services.UsuarioService;
import com.padima.microserviciousuario.model.Usuario;

@ExtendWith(MockitoExtension.class)
public class TestUsuarioService {

    @Mock
    private UsuarioRepository usuarioRepository;  // <<--- minúscula

    @InjectMocks
    private UsuarioService usuarioService;  // <<--- minúscula

    @Test
    public void testBuscarTodo() {
        List<Usuario> listaUsuario = new ArrayList<>();
        Usuario usuario1 = new Usuario();
        
        // Usa los campos REALES de tu clase Usuario
        usuario1.setRut(12345678L);
        usuario1.setNombre("Diego Sepulveda");
        usuario1.setMail("Diego@duocuc.cl");
        usuario1.setTelefono(98765432L);
        usuario1.setDireccion("Av. Libertador 123");

        listaUsuario.add(usuario1);

        when(usuarioRepository.findAll()).thenReturn(listaUsuario);
        List<Usuario> resultado = usuarioService.BuscarTodo();  // <<--- Mayúscula
        assertEquals(1, resultado.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    public void testBuscarUnUsuario() {
        Usuario usuario1 = new Usuario();
        usuario1.setRut(12345678L);
        usuario1.setNombre("Diego Sepulveda");

        when(usuarioRepository.findById(12345678L)).thenReturn(Optional.of(usuario1));
        Usuario usuarioBuscado = usuarioService.BuscarUsuario(12345678L);  // <<--- Mayúscula
        assertEquals(12345678L, usuarioBuscado.getRut());
        verify(usuarioRepository, times(1)).findById(12345678L);
    }

    @Test
    public void testGuardarUsuario() {
        Usuario usuario1 = new Usuario();
        usuario1.setRut(12345678L);
        usuario1.setNombre("Diego Sepulveda");

        when(usuarioRepository.save(usuario1)).thenReturn(usuario1);
        Usuario usuarioGuardado = usuarioService.GuardarUsuario(usuario1);  // <<--- Mayúscula
        assertEquals(12345678L, usuarioGuardado.getRut());
        verify(usuarioRepository, times(1)).save(usuario1);
    }

    @Test
    public void testEliminarUsuario() {
        Long rut = 12345678L;
        doNothing().when(usuarioRepository).deleteById(rut);

        usuarioService.EliminarUsuario(rut);  // <<--- Mayúscula
        verify(usuarioRepository, times(1)).deleteById(rut);
    }
}
