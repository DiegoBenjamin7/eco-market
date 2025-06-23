package com.padima.microserviciofactura.service;

import com.padima.microserviciofactura.assembler.FacturaAssembler;

// src/main/java/com/padima/microserviciofactura/service/FacturaService.java

import com.padima.microserviciofactura.dto.FacturaDTO;
import com.padima.microserviciofactura.model.Factura;
import com.padima.microserviciofactura.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacturaService {
    private final FacturaRepository facturaRepository;
    private final FacturaAssembler facturaAssembler;

    @Autowired
    public FacturaService(FacturaRepository facturaRepository, FacturaAssembler facturaAssembler) {
        this.facturaRepository = facturaRepository;
        this.facturaAssembler = facturaAssembler;
    }

    public FacturaDTO createFactura(FacturaDTO facturaDTO) {
        Factura factura = facturaAssembler.toEntity(facturaDTO);
        factura = facturaRepository.save(factura);
        return facturaAssembler.toDto(factura);
    }

    public FacturaDTO getFacturaById(Long id) {
        return facturaRepository.findById(id)
                .map(facturaAssembler::toDto)
                .orElseThrow(() -> new RuntimeException("Factura no ecnontrada"));
    }

    public List<FacturaDTO> getAllFacturas() {
        return facturaRepository.findAll().stream()
                .map(facturaAssembler::toDto)
                .collect(Collectors.toList());
    }

    public FacturaDTO updateFactura(Long id, FacturaDTO facturaDTO) {
        Factura existingFactura = facturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no ecnontrada"));

        existingFactura.setCustomerId(facturaDTO.getCustomerId());
        existingFactura.setProductIds(facturaDTO.getProductIds());
        existingFactura.setTotalAmount(facturaDTO.getTotalAmount());
        existingFactura.setStatus(facturaDTO.getStatus());

        Factura updatedFactura = facturaRepository.save(existingFactura);
        return facturaAssembler.toDto(updatedFactura);
    }

    public void deleteFactura(Long id) {
        facturaRepository.deleteById(id);
    }
}
