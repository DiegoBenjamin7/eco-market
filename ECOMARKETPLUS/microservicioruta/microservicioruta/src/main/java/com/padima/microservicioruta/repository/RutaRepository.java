package com.padima.microservicioruta.repository;

import com.padima.microservicioruta.model.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RutaRepository extends JpaRepository<Ruta, Long> {
    List<Ruta> findByEstado(String estado);
    List<Ruta> findByOrigenAndDestino(String origen, String destino);
    List<Ruta> findByTipoTransporte(String tipoTransporte);
    boolean existsByCodigoRuta(String codigoRuta);
}