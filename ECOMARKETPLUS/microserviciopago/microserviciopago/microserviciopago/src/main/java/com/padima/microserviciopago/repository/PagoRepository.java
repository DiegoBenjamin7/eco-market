package com.padima.microserviciopago.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.padima.microserviciopago.model.Pago;

public interface PagoRepository  extends JpaRepository<Pago, Long> {

}
