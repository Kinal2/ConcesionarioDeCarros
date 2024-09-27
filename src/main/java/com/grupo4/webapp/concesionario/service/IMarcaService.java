package com.grupo4.webapp.concesionario.service;

import java.util.List;

import com.grupo4.webapp.concesionario.model.Marca;

public interface IMarcaService {

    public List<Marca> listarMarcas();

    public Marca buscarMarcaPorId(Long id);

    public Boolean guardarMarca(Marca marca);

    public void eliminarMarca(Marca marca);

    public Boolean verificarMarcaDuplicada(Marca marcaNueva);
}
