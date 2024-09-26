package com.grupo4.webapp.concesionario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo4.webapp.concesionario.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
}
