package com.grupo4.webapp.concesionario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo4.webapp.concesionario.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,Long>{

}
