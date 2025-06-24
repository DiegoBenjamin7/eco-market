package com.padima.microserviciofactura.TestServiceFactura;

import com.padima.microserviciofactura.assembler.FacturaAssembler;
import com.padima.microserviciofactura.controller.FacturaController;
import com.padima.microserviciofactura.dto.FacturaDTO;
import com.padima.microserviciofactura.model.Factura;
import com.padima.microserviciofactura.model.Factura.FacturaStatus; // Importar FacturaStatus
import com.padima.microserviciofactura.repository.FacturaRepository;
import com.padima.microserviciofactura.service.FacturaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime; // Importar LocalDateTime
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class FacturaControllerTest {

    @Mock
    private FacturaService facturaService;

    @InjectMocks
    private FacturaController facturaController;

    private FacturaDTO testFacturaDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testFacturaDTO = new FacturaDTO();
        testFacturaDTO.setId(1L);
        testFacturaDTO.setCustomerId("CUST-001");
        testFacturaDTO.setProductIds(Arrays.asList("PROD-001", "PROD-002"));
        testFacturaDTO.setTotalAmount(150.75);
        // CORRECCIÓN: Asignar LocalDateTime y FacturaStatus correctamente
        testFacturaDTO.setIssueDate(LocalDateTime.of(2023, 10, 15, 14, 30, 0));
        testFacturaDTO.setStatus(FacturaStatus.PAID);
    }

    @Test
    @DisplayName("Crear una nueva factura")
    void createFactura_ShouldReturnCreatedFactura() {
        // Arrange
        when(facturaService.createFactura(any(FacturaDTO.class))).thenReturn(testFacturaDTO);

        // Act
        ResponseEntity<FacturaDTO> response = facturaController.createFactura(testFacturaDTO);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(testFacturaDTO, response.getBody());
        verify(facturaService, times(1)).createFactura(any(FacturaDTO.class));
    }

    @Test
    @DisplayName("Obtener factura por ID")
    void getFacturaById_ShouldReturnFactura() {
        // Arrange
        when(facturaService.getFacturaById(anyLong())).thenReturn(testFacturaDTO);

        // Act
        ResponseEntity<FacturaDTO> response = facturaController.getFacturaById(1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(testFacturaDTO, response.getBody());
        verify(facturaService, times(1)).getFacturaById(1L);
    }

    @Test
    @DisplayName("Obtener todas las facturas")
    void getAllFacturas_ShouldReturnListOfFacturas() {
        // Arrange
        List<FacturaDTO> facturaList = Arrays.asList(testFacturaDTO);
        when(facturaService.getAllFacturas()).thenReturn(facturaList);

        // Act
        ResponseEntity<List<FacturaDTO>> response = facturaController.getAllFacturas();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(facturaList, response.getBody());
        verify(facturaService, times(1)).getAllFacturas();
    }

    @Test
    @DisplayName("Actualizar factura existente")
    void updateFactura_ShouldReturnUpdatedFactura() {
        // Arrange
        when(facturaService.updateFactura(anyLong(), any(FacturaDTO.class))).thenReturn(testFacturaDTO);

        // Act
        ResponseEntity<FacturaDTO> response = facturaController.updateFactura(1L, testFacturaDTO);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(testFacturaDTO, response.getBody());
        verify(facturaService, times(1)).updateFactura(1L, testFacturaDTO);
    }

    @Test
    @DisplayName("Eliminar factura")
    void deleteFactura_ShouldReturnNoContent() {
        // Arrange
        doNothing().when(facturaService).deleteFactura(anyLong());

        // Act
        ResponseEntity<Void> response = facturaController.deleteFactura(1L);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        verify(facturaService, times(1)).deleteFactura(1L);
    }
}
