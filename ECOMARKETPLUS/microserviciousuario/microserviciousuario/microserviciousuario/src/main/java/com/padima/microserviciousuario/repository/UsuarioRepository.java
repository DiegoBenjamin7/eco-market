package com.padima.microserviciousuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.padima.microserviciousuario.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
