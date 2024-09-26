package com.grupo4.webapp.concesionario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.grupo4.webapp.concesionario.model.CategoriaCarro;

public interface CategoriaCarroRepository extends JpaRepository<CategoriaCarro, Long>{
    
}
