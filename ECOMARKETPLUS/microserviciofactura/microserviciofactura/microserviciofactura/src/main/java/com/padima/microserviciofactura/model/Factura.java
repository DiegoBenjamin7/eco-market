package com.padima.microserviciofactura.model;
// src/main/java/com/example/invoiceservice/model/Invoice.java


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

import com.padima.microserviciofactura.model.Factura.FacturaStatus;

@Data
@Entity
@Table(name = "Facturas")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String customerId;
    
    @ElementCollection
    private List<String> productIds;
    
    @Column(nullable = false)
    private Double totalAmount;
    
    @Column(nullable = false)
    private LocalDateTime issueDate;
    
    @Enumerated(EnumType.STRING)
    private FacturaStatus status;

    public enum FacturaStatus {
    PENDING,
    PAID,
    CANCELLED
    }
}
