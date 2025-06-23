package com.padima.microserviciofactura.dto;


import com.padima.microserviciofactura.model.Factura.FacturaStatus;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class FacturaDTO {
    private Long id;
    private String customerId;
    private List<String> productIds;
    private Double totalAmount;
    private LocalDateTime issueDate;
    private FacturaStatus status;
}
