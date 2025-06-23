package com.padima.microservicioenvio.repository;

import com.padima.microservicioenvio.model.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnvioRepository extends JpaRepository<Envio, Long> {
    List<Envio> findByEstado(String estado);
    List<Envio> findByIdRuta(Long idRuta);
    List<Envio> findByCodigoEnvioContaining(String codigo);
    boolean existsByCodigoEnvio(String codigoEnvio);
   }