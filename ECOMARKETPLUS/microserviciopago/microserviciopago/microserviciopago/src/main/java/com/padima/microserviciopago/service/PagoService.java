package com.padima.microserviciopago.service;

import com.padima.microserviciopago.model.Pago;
import com.padima.microserviciopago.repository.PagoRepository;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;


@Service
@Transactional
public class PagoService {

    // @Autowired
    private PagoRepository pagoRepository;

    public PagoService (PagoRepository pagoRepository){
        this.pagoRepository = pagoRepository;
    }

    public List<Pago> BuscarTodo(){
        return pagoRepository.findAll();
    }

    public Pago Buscar(long id){
        return pagoRepository.findById(id).get();
    }

    public Pago Guardar(Pago pago){
        return pagoRepository.save(pago);
    }

    public void Eliminar(long id){
        pagoRepository.deleteById(id);
    }

}