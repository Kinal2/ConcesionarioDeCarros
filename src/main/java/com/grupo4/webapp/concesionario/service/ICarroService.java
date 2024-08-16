package com.grupo4.webapp.concesionario.service;


import java.util.List;

import com.grupo4.webapp.concesionario.model.Carro;
import com.grupo4.webapp.concesionario.util.MethodType;

public interface ICarroService {
    public List<Carro> listarCarro();

    public Carro buscarCarroPorId(Long id);

    public Carro guardarCarro(Carro carro, MethodType methodType);

    public void eliminarCarro(Carro carro);

}
