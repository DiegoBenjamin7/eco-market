package com.padima.microservicioproducto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.padima.microservicioproducto.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{

    
} 