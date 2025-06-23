package com.padima.microserviciologin.repository;

import com.padima.microserviciologin.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LoginRepository extends JpaRepository<Login, Long> {
    

}

