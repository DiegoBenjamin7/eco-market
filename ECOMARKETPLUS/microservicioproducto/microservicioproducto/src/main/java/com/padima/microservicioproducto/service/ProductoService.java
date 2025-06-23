package com.padima.microservicioproducto.service;
import java.util.List;
import org.springframework.stereotype.Service;

import com.padima.microservicioproducto.model.Producto;
import com.padima.microservicioproducto.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {

    // @Autowired
    private ProductoRepository productoRepository;

    public ProductoService (ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }

    public List<Producto> buscarTodo(){
        return productoRepository.findAll();
    }

    public Producto buscar(Long idProducto){
        return productoRepository.findById(idProducto).get();
    }

    public Producto guardar(Producto producto){
        return productoRepository.save(producto);
    }

    public void eliminar(Long idProducto){
        productoRepository.deleteById(idProducto);
    }

}
