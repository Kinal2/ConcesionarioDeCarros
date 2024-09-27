package com.grupo4.webapp.concesionario.service;
import java.util.List;
import com.grupo4.webapp.concesionario.model.CategoriaCarro;
import com.grupo4.webapp.concesionario.util.MethodType;

public interface ICategoriaCarroService {

    public List<CategoriaCarro> listarCategoriaCarro();

    public CategoriaCarro buscarCategoriaCarro(Long id);

    public CategoriaCarro guardarCategoriaCarros(CategoriaCarro categoriaCarro, MethodType methodType);

    public void eliminarCategoriaCarros(CategoriaCarro categoriaCarro);

    public Boolean verificarCategoriaCarroDuplicado(CategoriaCarro categoriaCarroNuevo);

}
