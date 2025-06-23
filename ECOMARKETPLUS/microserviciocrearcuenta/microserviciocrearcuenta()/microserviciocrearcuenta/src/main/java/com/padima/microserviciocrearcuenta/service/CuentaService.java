package com.padima.microserviciocrearcuenta.service;

import com.padima.microserviciocrearcuenta.model.Cuenta;
import com.padima.microserviciocrearcuenta.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class CuentaService {
    @Autowired
    private CuentaRepository cuentaRepository;

    public List<Cuenta> buscarTodo() {
        return cuentaRepository.findAll();
    }

    public Cuenta buscar(Long id) {
        return cuentaRepository.findById(id).orElse(null);
    }

    public Cuenta guardar(Cuenta cuenta) {
        cuenta.setFechaCreacion(Instant.now());
        return cuentaRepository.save(cuenta);
    }

    public Cuenta actualizar(Long id, Cuenta cuentaActualizar) {
        Cuenta cuentaExistente = cuentaRepository.findById(id).orElse(null);
        if (cuentaExistente != null) {
            cuentaExistente.setUsername(cuentaActualizar.getUsername());
            cuentaExistente.setEmail(cuentaActualizar.getEmail());
            cuentaExistente.setPassword(cuentaActualizar.getPassword());
            cuentaExistente.setNombreCompleto(cuentaActualizar.getNombreCompleto());
            cuentaExistente.setEstado(cuentaActualizar.getEstado());
            cuentaExistente.setFechaActualizacion(Instant.now());
            return cuentaRepository.save(cuentaExistente);
        }
        return null;
    }

    public void eliminar(Long id) {
        cuentaRepository.deleteById(id);
    }

    public boolean existeUsername(String username) {
        return cuentaRepository.findByUsername(username) != null;
    }

    public boolean existeEmail(String email) {
        return cuentaRepository.findByEmail(email) != null;
    }
}
