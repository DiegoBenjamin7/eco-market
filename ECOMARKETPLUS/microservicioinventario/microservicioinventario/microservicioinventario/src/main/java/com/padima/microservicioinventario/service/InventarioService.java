package com.padima.microservicioinventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.padima.microservicioinventario.DTO.ProductoDTO;
import com.padima.microservicioinventario.model.Inventario;
import com.padima.microservicioinventario.repository.InventarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class InventarioService {

    // @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    public InventarioService(InventarioRepository inventarioRepository){
        this.inventarioRepository = inventarioRepository;
    }

    public List<Inventario> buscarTodo(){
        return inventarioRepository.findAll();
    }

    public Inventario buscar(Long idinvent){
        return inventarioRepository.findById(idinvent).get();
    }

    public Inventario guardar(Inventario inventario){
        return inventarioRepository.save(inventario);
    }

    public void eliminar(Long idinvent){
        inventarioRepository.deleteById(idinvent);
    }


    private  WebClient webClient;

    public InventarioService(WebClient webClient) {
        this.webClient = webClient;
    }

    public ProductoDTO BuscarProducto(Integer idProd){
        ProductoDTO producto = webClient.get()
                                        .uri("/{idProd}", idProd)
                                        .retrieve()
                                        .bodyToMono(ProductoDTO.class)
                                        .block();
        return producto;
    }


}
