package com.padima.microservicioorden.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.padima.microservicioorden.DTO.UsuarioDTO;
import com.padima.microservicioorden.model.Orden;
import com.padima.microservicioorden.repository.OrdenRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrdenService {

    private OrdenRepository ordenRepository;

    @Autowired
    public OrdenService (OrdenRepository ordenRepository){
        this.ordenRepository = ordenRepository;
    }


    public List<Orden> buscarTodo(){
        return ordenRepository.findAll();
    }

    public Orden buscar(Long id){
        return ordenRepository.findById(id).get();
    }

    public Orden guardar(Orden orden){
        return ordenRepository.save(orden);
    }

    public void eliminar(Long id){
        ordenRepository.deleteById(id);
    }

    private  WebClient webClient;

    public OrdenService(WebClient webClient) {
        this.webClient = webClient;
    }


    public UsuarioDTO BuscarUsuario(String rut){
        UsuarioDTO usuario = webClient.get()
                                        .uri("/{rut}", rut)
                                        .retrieve()
                                        .bodyToMono(UsuarioDTO.class)
                                        .block();
        return usuario;
    }

}
