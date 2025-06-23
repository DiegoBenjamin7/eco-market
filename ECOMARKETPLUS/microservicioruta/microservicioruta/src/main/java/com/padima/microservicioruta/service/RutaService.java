package com.padima.microservicioruta.service;

import com.padima.microservicioruta.model.Ruta;
import com.padima.microservicioruta.repository.RutaRepository;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RutaService {

    private final RutaRepository rutaRepository;

    // Método para crear ruta
    public Ruta crearRuta(Ruta ruta) {
        if (rutaRepository.existsByCodigoRuta(ruta.getCodigoRuta())) {
            throw new IllegalStateException("El código de ruta ya existe");
        }
        ruta.setEstado("ACTIVA"); // va a estar activa sisempre por defecto
        return rutaRepository.save(ruta);
    }

    public List<Ruta> obtenerRutasActivas() {
        return rutaRepository.findByEstado("ACTIVA");
    }


    public Ruta cambiarEstadoRuta(Long id, String nuevoEstado) {
        Ruta ruta = buscarRutaPorId(id);
        ruta.setEstado(nuevoEstado);
        return rutaRepository.save(ruta);
    }


    public List<Ruta> buscarRutasDisponibles(String origen, String destino, String tipoTransporte) {
        if (origen != null && destino != null) {
            return rutaRepository.findByOrigenAndDestino(origen, destino);
        }
        // Otras combinaciones de búsqueda...
        return rutaRepository.findAll();
    }

    public Ruta buscarRutaPorId(Long id) {
    return rutaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Ruta no encontrada"));
}

public Ruta actualizarRuta(Long id, Ruta rutaActualizada) {
    return rutaRepository.findById(id)
            .map(ruta -> {
                ruta.setCodigoRuta(rutaActualizada.getCodigoRuta());
                ruta.setOrigen(rutaActualizada.getOrigen());
                ruta.setDestino(rutaActualizada.getDestino());
                ruta.setDistanciaKm(rutaActualizada.getDistanciaKm());
                ruta.setTiempoEstimadoMinutos(rutaActualizada.getTiempoEstimadoMinutos());
                ruta.setTipoTransporte(rutaActualizada.getTipoTransporte());
                ruta.setEstado(rutaActualizada.getEstado());
                return rutaRepository.save(ruta);
            })
            .orElseGet(() -> {
                rutaActualizada.setId(id);
                return rutaRepository.save(rutaActualizada);
            });
}

public void eliminarRuta(Long id) {
    rutaRepository.deleteById(id);
}

public List<Ruta> buscarPorTipoTransporte(String tipoTransporte) {
    return rutaRepository.findByTipoTransporte(tipoTransporte);
}}