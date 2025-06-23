package com.padima.microservicioinventario.testInventarioService;

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

import com.padima.microservicioinventario.repository.InventarioRepository;
import com.padima.microservicioinventario.service.InventarioService;
import com.padima.microservicioinventario.model.Inventario;



@ExtendWith(MockitoExtension.class)
public class TestInventarioService {

    @Mock
    private InventarioRepository inventarioRepository;

    @InjectMocks
    private InventarioService inventarioService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBuscarTodo(){
        List<Inventario> listaInventario = new ArrayList<>();

        Inventario i1 = new Inventario();

        i1.setIdinventario(1L);
        i1.setIdProducto(212);
        i1.setStockActual(90);
        i1.setUbicacionBodega("Almacén Santiago");


        listaInventario.add(i1);

        when(inventarioRepository.findAll()).thenReturn(listaInventario);
        List<Inventario> resultadoBucado = inventarioService.buscarTodo();
        assertEquals(1, resultadoBucado.size());
        verify(inventarioRepository, times(1)).findAll();

    }

    @Test
    public void testBuscarUnInventario(){

        Inventario i1 = new Inventario();

        i1.setIdinventario(1L);
        i1.setIdProducto(212);
        i1.setStockActual(90);
        i1.setUbicacionBodega("Almacén Santiago");

        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(i1));
        Inventario inventarioBuscado = inventarioService.buscar(1L);
        assertEquals(1, inventarioBuscado.getIdinventario());
        verify(inventarioRepository, times(1)).findById(1L);

    }

    @Test
    public void testGuardarInventario(){

        Inventario i1 = new Inventario();

        i1.setIdinventario(1L);
        i1.setIdProducto(212);
        i1.setStockActual(90);
        i1.setUbicacionBodega("Almacén Santiago");

        when(inventarioRepository.save(i1)).thenReturn(i1);
        Inventario inventarioGuardada = inventarioService.guardar(i1);
        assertEquals(1, inventarioGuardada.getIdinventario());
        verify(inventarioRepository, times(1)).save(i1);
    }

    @Test
    public void testEliminarInventario(){
        Long idInventario = 1L; 
        doNothing().when(inventarioRepository).deleteById(idInventario);

        inventarioService.eliminar(idInventario);
        verify(inventarioRepository, times(1)).deleteById(idInventario);
    }

}
