package com.grupo4.webapp.concesionario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo4.webapp.concesionario.model.CategoriaCarro;
import com.grupo4.webapp.concesionario.repository.CategoriaCarroRepository;
import com.grupo4.webapp.concesionario.util.ConcesionarioAlert;
import com.grupo4.webapp.concesionario.util.MethodType;

import javafx.scene.control.ButtonType;

@Service
public class CategoriaCarroService implements ICategoriaCarroService{
    
    @Autowired
    private CategoriaCarroRepository categoriaCarroRepository;

    @Autowired
    private ConcesionarioAlert concesionarioAlert;

    @Override
    public List<CategoriaCarro> listarCategoriaCarro() {
        return categoriaCarroRepository.findAll();
    }

    @Override
    public CategoriaCarro buscarCategoriaCarro(Long id) {
        return categoriaCarroRepository.findById(id).orElse(null);
    }

    @Override
    public CategoriaCarro guardarCategoriaCarros(CategoriaCarro categoriaCarro, MethodType methodType) {
        if(methodType.equals(MethodType.POST)){
            if (!verificarCategoriaCarroDuplicado(categoriaCarro)) {
                concesionarioAlert.mostrarAlertaInfo(600);
                return categoriaCarroRepository.save(categoriaCarro);
            } else {
                concesionarioAlert.mostrarAlertaInfo(580);
            }
        }else if(methodType == MethodType.PUT){
            try {
                if(concesionarioAlert.mostrarAlertaConfirmacion(107).get() == ButtonType.OK) {
                    if(!verificarCategoriaCarroDuplicado(categoriaCarro)){
                        concesionarioAlert.mostrarAlertaInfo(601);
                        return categoriaCarroRepository.save(categoriaCarro);
                    }else{
                        concesionarioAlert.mostrarAlertaInfo(580);
                    }
                }
            } catch (Exception e) {
                concesionarioAlert.mostrarAlertaInfo(404);
            }
        }
        return categoriaCarro;
    }

    @Override
    public void eliminarCategoriaCarros(CategoriaCarro categoriaCarro) {
        try {
            if(concesionarioAlert.mostrarAlertaConfirmacion(406).get() == ButtonType.OK){
                categoriaCarroRepository.delete(categoriaCarro);
                concesionarioAlert.mostrarAlertaInfo(401);
            }
        } catch (Exception e) {
            concesionarioAlert.mostrarAlertaInfo(404);
        }
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
