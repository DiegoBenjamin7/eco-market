package com.padima.microservicioproveedores.testProveedoresService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.padima.microservicioproveedores.model.proveedores;
import com.padima.microservicioproveedores.repository.ProveedoresRepository;
import com.padima.microservicioproveedores.service.proveedoresService;

@ExtendWith(MockitoExtension.class)
public class TestProveedoresService {

    @Mock
    private ProveedoresRepository proveedoresRepository;

    @InjectMocks
    private proveedoresService proveedoresService;

    @Test
    public void testCrearProveedorExitoso() {
        proveedores proveedor = crearProveedorEjemplo();
        proveedor.setRutEmpresa("12345678-9");
        
        when(proveedoresRepository.existsByRutEmpresa("12345678-9")).thenReturn(false);
        when(proveedoresRepository.save(proveedor)).thenReturn(proveedor);
        
        proveedores resultado = proveedoresService.crearProveedor(proveedor);
        
        assertNotNull(resultado);
        assertEquals("Ecologico", resultado.getTipo()); // Verificamos el tipo
        verify(proveedoresRepository, times(1)).save(proveedor);
    }

    @Test
    public void testBuscarProveedoresPorTipo() {
        List<proveedores> proveedoresTecnologia = Arrays.asList(
            crearProveedorEjemploConTipo("Ecologico"),
            crearProveedorEjemploConTipo("Ecologico")
        );
        
        when(proveedoresRepository.findByTipo("Ecologico")).thenReturn(proveedoresTecnologia);
        
        List<proveedores> resultado = proveedoresService.buscarProveedoresPorTipo("Ecologico");
        
        assertEquals(2, resultado.size());
        resultado.forEach(p -> assertEquals("Ecologico", p.getTipo()));
    }

    @Test
    public void testActualizarTipoProveedor() {
        Long proveedorId = 1L;
        proveedores proveedorExistente = crearProveedorEjemploConTipo("Ecologico");
        proveedores datosActualizados = crearProveedorEjemploConTipo("Ecologico");
        
        when(proveedoresRepository.findById(proveedorId)).thenReturn(Optional.of(proveedorExistente));
        when(proveedoresRepository.save(any(proveedores.class))).thenReturn(datosActualizados);
        
        proveedores resultado = proveedoresService.actualizarProveedor(proveedorId, datosActualizados);
        
        assertEquals("Ecologico", resultado.getTipo());
        verify(proveedoresRepository, times(1)).save(any(proveedores.class));
    }

    private proveedores crearProveedorEjemplo() {
        proveedores p = new proveedores();
        p.setId(1L);
        p.setNombre("Nike Ecologico");
        p.setDireccion("Av Simpson 123");
        p.setEmail("proveedor@naiki.com");
        p.setTelefono("+56 9 12345678");
        p.setTipo("Ecologico"); // Tipo como categoría
        p.setRutEmpresa("12345678-9");
        return p;
    }
    
    private proveedores crearProveedorEjemploConTipo(String tipo) {
        proveedores p = crearProveedorEjemplo();
        p.setTipo(tipo);
        return p;
    }
}