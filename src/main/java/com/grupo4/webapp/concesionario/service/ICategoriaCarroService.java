package com.grupo4.webapp.concesionario.service;
import java.util.List;
import com.grupo4.webapp.concesionario.model.CategoriaCarro;

public interface ICategoriaCarroService {

    public List<CategoriaCarro> listarCategoriaCarro();

    public CategoriaCarro buscarCategoriaCarro(Long id);

    public Boolean guardarCategoriaCarros(CategoriaCarro categoriaCarro);

    public void eliminarCategoriaCarros(CategoriaCarro categoriaCarro);

    public Boolean verificarCategoriaCarroDuplicado(CategoriaCarro categoriaCarroNuevo);

}
