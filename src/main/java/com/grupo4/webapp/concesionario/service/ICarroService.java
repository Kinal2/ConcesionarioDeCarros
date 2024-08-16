package com.grupo4.webapp.concesionario.service;

import java.util.List;

import com.grupo4.webapp.concesionario.model.Carro;

public interface ICarroService {
    public List<Carro> listarCarro();

    public Carro buscarCarroPorId(Long id);

    public Carro guardarCarro(Carro carro);

    public void eliminarCarro(Carro carro);

}
