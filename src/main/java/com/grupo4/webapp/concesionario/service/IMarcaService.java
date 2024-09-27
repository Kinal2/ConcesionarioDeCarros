package com.grupo4.webapp.concesionario.service;

import java.util.List;

import com.grupo4.webapp.concesionario.model.Marca;
import com.grupo4.webapp.concesionario.util.MethodType;

public interface IMarcaService {

    public List<Marca> listarMarcas();

    public Marca buscarMarcaPorId(Long id);

    public Marca guardarMarca(Marca marca, MethodType methodType);

    public void eliminarMarca(Marca marca);

    public Boolean verificarMarcaDuplicada(Marca marcaNueva);
}
