package com.padima.microservicioenvio.service;

import com.padima.microservicioenvio.model.Envio;
import com.padima.microservicioenvio.repository.EnvioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class EnvioService {

    @Autowired
    private EnvioRepository envioRepository;

    // ===== CRUD BÁSICO =====
    public Envio crearEnvio(Envio envio) {
    if (envioRepository.existsByCodigoEnvio(envio.getCodigoEnvio())) {
        throw new IllegalStateException("El código de envío ya existe");
    }
    envio.setEstado("PREPARACION");
    return envioRepository.save(envio);
}
    public Optional<Envio> buscarEnvioPorId(Long id) {
        return envioRepository.findById(id);
    }

    public List<Envio> listarTodosLosEnvios() {
        return envioRepository.findAll();
    }

    public Envio actualizarEnvio(Long id, Envio datosActualizados) {
        return envioRepository.findById(id)
            .map(envioExistente -> {
                // Actualiza solo los campos permitidos
                if (datosActualizados.getDireccionDestino() != null) {
                    envioExistente.setDireccionDestino(datosActualizados.getDireccionDestino());
                }
                if (datosActualizados.getEstado() != null) {
                    envioExistente.setEstado(datosActualizados.getEstado());
                }
                // Agrega más campos según tu modelo
                return envioRepository.save(envioExistente);
            })
            .orElseThrow(() -> new RuntimeException("Envío no encontrado"));
    }

    public void eliminarEnvio(Long id) {
        envioRepository.deleteById(id);
    }

    // ===== MÉTODOS ESPECÍFICOS (según tu código original) =====
    public List<Envio> obtenerEnviosPorEstado(String estado) {
        return envioRepository.findByEstado(estado);
    }

    public Envio cambiarEstadoEnvio(Long id, String nuevoEstado) {
        Envio envio = envioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Envío no encontrado"));
        envio.setEstado(nuevoEstado);
        return envioRepository.save(envio);
    }

    public List<Envio> buscarEnvios(String codigo, Long idRuta, String estado) {
        if (codigo != null) {
            return envioRepository.findByCodigoEnvioContaining(codigo);
        } else if (idRuta != null) {
            return envioRepository.findByIdRuta(idRuta);
        } else if (estado != null) {
            return obtenerEnviosPorEstado(estado);
        }
        return listarTodosLosEnvios();
    }
}
