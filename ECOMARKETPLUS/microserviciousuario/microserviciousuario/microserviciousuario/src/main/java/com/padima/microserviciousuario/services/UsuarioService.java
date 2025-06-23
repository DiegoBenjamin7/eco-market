package com.padima.microserviciousuario.services;

import java.util.List;

import com.padima.microserviciousuario.model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.padima.microserviciousuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuariorepository;

    public List<Usuario> BuscarTodo(){
        return usuariorepository.findAll();
    }

    public Usuario BuscarUsuario(Long rut){
        return usuariorepository.findById(rut).get();
    }

    public Usuario GuardarUsuario(Usuario usuario){
        return usuariorepository.save(usuario);
    }

    public void EliminarUsuario(Long rut){
        usuariorepository.deleteById(rut);
    }

}
