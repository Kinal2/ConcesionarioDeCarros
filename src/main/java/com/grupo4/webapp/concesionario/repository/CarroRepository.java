package com.grupo4.webapp.concesionario.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo4.webapp.concesionario.model.Carro;
import com.grupo4.webapp.concesionario.util.EstadoCarro;

public interface CarroRepository extends JpaRepository<Carro, Long>{
    List<Carro> findByEstado(EstadoCarro estado);
}
