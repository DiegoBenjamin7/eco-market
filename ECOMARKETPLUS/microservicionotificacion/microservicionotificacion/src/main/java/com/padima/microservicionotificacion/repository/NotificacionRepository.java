package com.padima.microservicionotificacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.padima.microservicionotificacion.model.Notificacion;


public interface NotificacionRepository  extends JpaRepository<Notificacion, Long>{

}