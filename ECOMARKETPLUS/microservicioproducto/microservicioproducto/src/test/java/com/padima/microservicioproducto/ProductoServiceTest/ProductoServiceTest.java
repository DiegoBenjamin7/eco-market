package com.padima.microservicioproducto.ProductoServiceTest;

import com.padima.microservicioproducto.model.Producto;
import com.padima.microservicioproducto.repository.ProductoRepository;
import com.padima.microservicioproducto.service.ProductoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto producto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        producto = new Producto(1L, "Producto Test", "Categoría Test", 10, 1000, null);
    }

    @Test
    public void testBuscarTodo() {
        when(productoRepository.findAll()).thenReturn(Arrays.asList(producto));

        var productos = productoService.buscarTodo();

        assertEquals(1, productos.size());
        assertEquals("Producto Test", productos.get(0).getNombreProducto());
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    public void testBuscarPorId() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        var resultado = productoService.buscar(1L);

        assertNotNull(resultado);
        assertEquals("Producto Test", resultado.getNombreProducto());
        verify(productoRepository, times(1)).findById(1L);
    }

    @Test
    public void testGuardarProducto() {
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        var resultado = productoService.guardar(producto);

        assertNotNull(resultado);
        assertEquals("Producto Test", resultado.getNombreProducto());
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    public void testEliminarProducto() {
        doNothing().when(productoRepository).deleteById(1L);

        productoService.eliminar(1L);

        verify(productoRepository, times(1)).deleteById(1L);
    }
}
