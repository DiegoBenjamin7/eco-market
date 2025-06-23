package com.padima.microservicioproveedores.repository;

import com.padima.microservicioproveedores.model.proveedores;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProveedoresRepository extends JpaRepository<proveedores, Long> {
    boolean existsByRutEmpresa(String rutEmpresa);
    List<proveedores> findByTipo(String tipo);
    List<proveedores> findByTipoAndNombreContainingIgnoreCase(String tipo, String nombre);
    List<proveedores> findByNombreContainingIgnoreCase(String nombre);
    proveedores findByRutEmpresa(String rutEmpresa);
}