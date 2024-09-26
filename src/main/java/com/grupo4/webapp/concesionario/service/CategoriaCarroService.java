package com.grupo4.webapp.concesionario.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo4.webapp.concesionario.model.CategoriaCarro;
import com.grupo4.webapp.concesionario.repository.CategoriaCarroRepository;

@Service
public class CategoriaCarroService implements ICategoriaCarroService{
    
    @Autowired
    private CategoriaCarroRepository categoriaCarroRepository;

    @Override
    public List<CategoriaCarro> listarCategoriaCarro() {
        return categoriaCarroRepository.findAll();
    }

    @Override
    public CategoriaCarro buscarCategoriaCarro(Long id) {
        return categoriaCarroRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean guardarCategoriaCarros(CategoriaCarro categoriaCarro) {
        if (!verificarCategoriaCarroDuplicado(categoriaCarro)) {
            categoriaCarroRepository.save(categoriaCarro);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void eliminarCategoriaCarros(CategoriaCarro categoriaCarro) {
        categoriaCarroRepository.delete(categoriaCarro);
    }

    @Override
    public Boolean verificarCategoriaCarroDuplicado(CategoriaCarro categoriaCarroNuevo) {
        List<CategoriaCarro> categoriaCarros = listarCategoriaCarro();
        Boolean flag = false;

        for (CategoriaCarro categoriaCarro : categoriaCarros) {
            if(categoriaCarro.getNombreCategoriaCarro().equals(categoriaCarroNuevo.getNombreCategoriaCarro()) && !categoriaCarro.getId().equals(categoriaCarroNuevo.getId())){
                flag = true;
            }
        }
        return flag;
    }
}
