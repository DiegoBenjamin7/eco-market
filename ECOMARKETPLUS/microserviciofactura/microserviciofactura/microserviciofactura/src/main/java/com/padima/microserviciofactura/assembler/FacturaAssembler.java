package com.padima.microserviciofactura.assembler;
// src/main/java/com/padima/microserviciofactura/assembler/FacturaAssembler.java

import com.padima.microserviciofactura.dto.FacturaDTO;
import com.padima.microserviciofactura.model.Factura;
import com.padima.microserviciofactura.dto.FacturaDTO;
import com.padima.microserviciofactura.model.Factura;

import org.springframework.stereotype.Component;
@Component
public class FacturaAssembler {
    public FacturaDTO toDto(Factura factura) {
        FacturaDTO dto = new FacturaDTO();
        dto.setId(factura.getId());
        dto.setCustomerId(factura.getCustomerId());
        dto.setProductIds(factura.getProductIds());
        dto.setTotalAmount(factura.getTotalAmount());
        dto.setIssueDate(factura.getIssueDate());
        dto.setStatus(factura.getStatus());
        return dto;
    }
    public Factura toEntity(FacturaDTO dto) {
        Factura factura = new Factura();
        factura.setCustomerId(dto.getCustomerId());
        factura.setProductIds(dto.getProductIds());
        factura.setTotalAmount(dto.getTotalAmount());
        factura.setIssueDate(dto.getIssueDate());
        factura.setStatus(dto.getStatus());
        return factura;
    }
}