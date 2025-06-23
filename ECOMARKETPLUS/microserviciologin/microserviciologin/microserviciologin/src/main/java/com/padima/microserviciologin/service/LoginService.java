package com.padima.microserviciologin.service;

import com.padima.microserviciologin.model.Login;
import com.padima.microserviciologin.repository.LoginRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;


@Service
@Transactional
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository){
        this.loginRepository = loginRepository;
    }

    public List<Login> buscarTodo(){
        return loginRepository.findAll();
    }

    public Login buscar(Long id){
        return loginRepository.findById(id).get();
    }

    public Login guardar(Login login){
        return loginRepository.save(login);
    }

    public void eliminar(Long id){
        loginRepository.deleteById(id);
    }

}
