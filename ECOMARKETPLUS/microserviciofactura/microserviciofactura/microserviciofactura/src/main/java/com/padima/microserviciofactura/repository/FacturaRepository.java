package com.padima.microserviciofactura.repository;
// src/main/java/com/example/invoiceservice/repository/InvoiceRepository.java


import com.padima.microserviciofactura.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
}
