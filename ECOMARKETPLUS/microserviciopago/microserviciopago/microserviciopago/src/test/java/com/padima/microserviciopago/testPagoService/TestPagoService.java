package com.padima.microserviciopago.testPagoService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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

import com.padima.microserviciopago.repository.PagoRepository;
import com.padima.microserviciopago.service.PagoService;
import com.padima.microserviciopago.model.Pago;




@ExtendWith(MockitoExtension.class)
public class TestPagoService {

    @Mock
    private PagoRepository pagoRepository;

    @InjectMocks
    private PagoService pagoService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBuscarTodo(){
        List<Pago> listaPago = new ArrayList<>();

        Pago p1 = new Pago();

        p1.setIdpago(1L);
        p1.setOrdenId(2L);
        p1.setMonto(50000);
        p1.setMetodoPago("Debito");
        p1.setEstadoPago("Rechazado");
        p1.setFechaPago(LocalDate.parse("2026-09-12"));


        listaPago.add(p1);

        when(pagoRepository.findAll()).thenReturn(listaPago);
        List<Pago> resultadoBucado = pagoService.BuscarTodo();
        assertEquals(1, resultadoBucado.size());
        verify(pagoRepository, times(1)).findAll();

    }

    @Test
    public void testBuscarUnPago(){

        Pago p1 = new Pago();

        p1.setIdpago(1L);
        p1.setOrdenId(2L);
        p1.setMonto(50000);
        p1.setMetodoPago("Debito");
        p1.setEstadoPago("Rechazado");
        p1.setFechaPago(LocalDate.parse("2026-09-12"));

        when(pagoRepository.findById(1L)).thenReturn(Optional.of(p1));
        Pago pagoBuscado = pagoService.Buscar(1L);
        assertEquals(1, pagoBuscado.getIdpago());
        verify(pagoRepository, times(1)).findById(1L);

    }

    @Test
    public void testGuardarPago(){

        Pago p1 = new Pago();

        p1.setIdpago(1L);
        p1.setOrdenId(2L);
        p1.setMonto(50000);
        p1.setMetodoPago("Debito");
        p1.setEstadoPago("Rechazado");
        p1.setFechaPago(LocalDate.parse("2026-09-12"));

        when(pagoRepository.save(p1)).thenReturn(p1);
        Pago pagoGuardado = pagoService.Guardar(p1);
        assertEquals(1, pagoGuardado.getIdpago());
        verify(pagoRepository, times(1)).save(p1);
    }

    @Test
    public void testEliminarPago(){
        Long idPago = 1L; 
        doNothing().when(pagoRepository).deleteById(idPago);

        pagoService.Eliminar(idPago);
        verify(pagoRepository, times(1)).deleteById(idPago);
    }

}
