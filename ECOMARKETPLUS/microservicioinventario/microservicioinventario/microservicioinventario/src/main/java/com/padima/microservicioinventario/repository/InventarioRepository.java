package com.padima.microservicioinventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.padima.microservicioinventario.model.Inventario;

public interface InventarioRepository extends JpaRepository<Inventario, Long>{

    
} 
