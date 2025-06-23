package com.padima.microservicioorden.repository;

import com.padima.microservicioorden.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrdenRepository extends JpaRepository<Orden, Long> {

}



