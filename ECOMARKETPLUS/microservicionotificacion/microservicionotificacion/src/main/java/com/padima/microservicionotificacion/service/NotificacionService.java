 package com.padima.microservicionotificacion.service;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;



import com.padima.microservicionotificacion.dto.UsuarioDto;

import com.padima.microservicionotificacion.model.Notificacion;

import com.padima.microservicionotificacion.repository.NotificacionRepository;



import jakarta.transaction.Transactional;

import java.util.List;



@Service

@Transactional

public class NotificacionService {



  private final NotificacionRepository notificacionRepository;

  private final WebClient webClient;



  @Autowired

  public NotificacionService(NotificacionRepository notificacionRepository, WebClient webClient) {

    this.notificacionRepository = notificacionRepository;

    this.webClient = webClient;

  }



  public UsuarioDto BuscarUsuario(String run) {

    return webClient.get()

        .uri("/{run}", run)

        .retrieve()

        .bodyToMono(UsuarioDto.class)

        .block();

  }



  public List<Notificacion> buscarTodo() {

    return notificacionRepository.findAll();

  }



  public Notificacion buscar(long id) {

    return notificacionRepository.findById(id).orElseThrow(() ->

      new RuntimeException("Notificación no encontrada con ID: " + id));

  }



  public Notificacion guardar(Notificacion notificacion) {

    return notificacionRepository.save(notificacion);

  }



  public void eliminar(long id) {

    notificacionRepository.deleteById(id);

  }

}