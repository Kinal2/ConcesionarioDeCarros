package com.grupo4.webapp.concesionario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo4.webapp.concesionario.model.Carro;

public interface CarroRepository extends JpaRepository<Carro, Long>{
    
}