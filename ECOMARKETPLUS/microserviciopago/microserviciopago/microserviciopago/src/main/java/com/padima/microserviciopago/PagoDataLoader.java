package com.padima.microserviciopago;
import com.padima.microserviciopago.model.Pago;
import com.padima.microserviciopago.repository.PagoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
@Configuration
public class PagoDataLoader {
    private static final String[] METODOS_PAGO = {"CREDITO", "DEBITO", "TRANSFERENCIA", "EFECTIVO", "CRYPTO"};
    private static final String[] ESTADOS_PAGO = {"EXITOSO", "PENDIENTE", "RECHAZADO", "REEMBOLSADO", "EN_PROCESO"};
    @Bean
    CommandLineRunner initDatabase(PagoRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                List<Pago> pagos = Arrays.asList(
                    crearPago(1001L, 49990, LocalDate.now().minusDays(2)),
                    crearPago(1002L, 129990, LocalDate.now().minusDays(5)),
                    crearPago(1003L, 25990, LocalDate.now().minusWeeks(1)),
                    crearPago(1004L, 89990, LocalDate.now().minusDays(1)),
                    crearPago(1005L, 14990, LocalDate.now().minusMonths(1)),
                    crearPago(1006L, 199990, LocalDate.now().minusDays(3)),
                    crearPago(1007L, 29990, LocalDate.now().minusWeeks(2))
                );
                repository.saveAll(pagos);
            }
        };
    }
    private Pago crearPago(Long ordenId, Integer monto, LocalDate fechaBase) {Random random = new Random();
        
        Pago pago = new Pago();
        pago.setOrdenId(ordenId);
        pago.setMonto(monto);
        pago.setMetodoPago(METODOS_PAGO[random.nextInt(METODOS_PAGO.length)]);
        pago.setEstadoPago(ESTADOS_PAGO[random.nextInt(ESTADOS_PAGO.length)]);
        pago.setFechaPago(fechaBase.plusDays(random.nextInt(3))); // Variación de ±3 días
        
        return pago;
    }
}
