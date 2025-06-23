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

    private  WebClient webClient;

    public NotificacionService(WebClient webClient) {
        this.webClient = webClient;
    }


    public UsuarioDto BuscarUsuario(String run){
        UsuarioDto usuario = webClient.get()
                                        .uri("/{run}", run)
                                        .retrieve()
                                        .bodyToMono(UsuarioDto.class)
                                        .block();
        return usuario;
    }

    
    private NotificacionRepository notificacionRepository;

    @Autowired
    public NotificacionService(NotificacionRepository notificacionRepository){
        this.notificacionRepository = notificacionRepository;
    }

    public List<Notificacion> buscarTodo(){
        return notificacionRepository.findAll();
    }

    public Notificacion buscar(long id){
        return notificacionRepository.findById(id).get();
    }

    public Notificacion guardar(Notificacion notificacion){
        return notificacionRepository.save(notificacion);
    }

    public void eliminar(long id){
        notificacionRepository.deleteById(id);
    }

}
