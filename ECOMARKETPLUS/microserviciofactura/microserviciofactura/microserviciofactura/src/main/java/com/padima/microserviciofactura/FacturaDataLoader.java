package com.padima.microserviciofactura;

import com.padima.microserviciofactura.model.Factura;
import com.padima.microserviciofactura.model.Factura.FacturaStatus;
import com.padima.microserviciofactura.repository.FacturaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Configuration
public class FacturaDataLoader {

    @Bean
    CommandLineRunner initDatabase(FacturaRepository repository) {
        return args -> {
            // Verificar si ya existen datos para no duplicar
            if (repository.count() == 0) {
                List<Factura> facturas = Arrays.asList(
                    // Factura reciente PENDIENTE
                    crearFactura("CUST-001", Arrays.asList("PROD-100", "PROD-101"), 1250.75, 
                                LocalDateTime.now().minusDays(2), FacturaStatus.PENDING),
                    
                    // Factura antigua PAGADA
                    crearFactura("CUST-002", Arrays.asList("PROD-200", "PROD-201", "PROD-202"), 1899.99, 
                                LocalDateTime.now().minusMonths(1), FacturaStatus.PAID),
                    
                    // Factura CANCELADA
                    crearFactura("CUST-003", Arrays.asList("PROD-300"), 450.50, 
                                LocalDateTime.now().minusWeeks(3), FacturaStatus.CANCELLED),
                    
                    // Factura con múltiples productos
                    crearFactura("CUST-001", Arrays.asList("PROD-102", "PROD-103", "PROD-104", "PROD-105"), 3200.00, 
                                LocalDateTime.now().minusDays(5), FacturaStatus.PENDING)
                );

                repository.saveAll(facturas);
            }
        };
    }

    private Factura crearFactura(String customerId, List<String> productIds, 
                               Double totalAmount, LocalDateTime issueDate, 
                               FacturaStatus status) {
        Factura factura = new Factura();
        factura.setCustomerId(customerId);
        factura.setProductIds(productIds);
        factura.setTotalAmount(totalAmount);
        factura.setIssueDate(issueDate);
        factura.setStatus(status);
        return factura;
    }
}
