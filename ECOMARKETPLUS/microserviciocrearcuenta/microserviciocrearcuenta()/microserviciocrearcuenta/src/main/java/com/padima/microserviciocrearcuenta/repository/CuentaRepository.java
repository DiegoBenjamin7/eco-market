package com.padima.microserviciocrearcuenta.repository;

import com.padima.microserviciocrearcuenta.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    Cuenta findByUsername(String username);
    Cuenta findByEmail(String email);
}
