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
        // Validaciones previas
        if (cuenta.getUsername() == null || cuenta.getUsername().isEmpty()) {
            throw new IllegalArgumentException("El username es requerido");
        }
        
        if (cuentaRepository.existsByUsername(cuenta.getUsername())) {
            throw new IllegalArgumentException("El username '" + cuenta.getUsername() + "' ya está registrado");
        }
        
        if (cuentaRepository.existsByEmail(cuenta.getEmail())) {
            throw new IllegalArgumentException("El email '" + cuenta.getEmail() + "' ya está registrado");
        }

        cuenta.setFechaCreacion(Instant.now());
        return cuentaRepository.save(cuenta);
    }

    public Cuenta actualizar(Long id, Cuenta cuentaActualizar) {
        Cuenta cuentaExistente = cuentaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada con ID: " + id));

        // Validar unicidad solo si cambian los campos únicos
        if (!cuentaExistente.getUsername().equals(cuentaActualizar.getUsername())) {
            if (cuentaRepository.existsByUsername(cuentaActualizar.getUsername())) {
                throw new IllegalArgumentException("El nuevo username ya está en uso");
            }
            cuentaExistente.setUsername(cuentaActualizar.getUsername());
        }

        if (!cuentaExistente.getEmail().equals(cuentaActualizar.getEmail())) {
            if (cuentaRepository.existsByEmail(cuentaActualizar.getEmail())) {
                throw new IllegalArgumentException("El nuevo email ya está en uso");
            }
            cuentaExistente.setEmail(cuentaActualizar.getEmail());
        }

        // Actualizar otros campos
        cuentaExistente.setPassword(cuentaActualizar.getPassword());
        cuentaExistente.setNombreCompleto(cuentaActualizar.getNombreCompleto());
        cuentaExistente.setEstado(cuentaActualizar.getEstado());
        cuentaExistente.setFechaActualizacion(Instant.now());

        return cuentaRepository.save(cuentaExistente);
    }

    public void eliminar(Long id) {
        if (!cuentaRepository.existsById(id)) {
            throw new IllegalArgumentException("Cuenta no encontrada con ID: " + id);
        }
        cuentaRepository.deleteById(id);
    }

    public boolean existeUsername(String username) {
        return cuentaRepository.existsByUsername(username);
    }

    public boolean existeEmail(String email) {
        return cuentaRepository.existsByEmail(email);
    }
}