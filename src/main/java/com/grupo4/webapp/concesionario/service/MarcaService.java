package com.grupo4.webapp.concesionario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo4.webapp.concesionario.model.Marca;
import com.grupo4.webapp.concesionario.repository.MarcaRepository;

@Service
public class MarcaService implements IMarcaService{

    @Autowired
    private MarcaRepository marcaRepository;

    @Override
    public List<Marca> listarMarcas() {
        return marcaRepository.findAll();
    }

    @Override
    public Marca buscarMarcaPorId(Long id) {
        return marcaRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean guardarMarca(Marca marca) {
       if(!verificarMarcaDuplicada(marca)){
            marcaRepository.save(marca);
            return true;
       }
       return false;
    }

    @Override
    public void eliminarMarca(Marca marca) {
        marcaRepository.delete(marca);
    }

    @Override
    public Boolean verificarMarcaDuplicada(Marca marcaNueva) {
        List<Marca> marcas = listarMarcas();
        Boolean flag = false;

        for(Marca marca : marcas){
            if(marca.getNombreMarca().equals(marcaNueva.getNombreMarca()) && !marca.getId().equals(marcaNueva.getId())){
                flag = true;
            }
        }
        return flag;
    }

}
