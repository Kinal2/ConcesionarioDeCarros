package com.grupo4.webapp.concesionario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo4.webapp.concesionario.model.Marca;
import com.grupo4.webapp.concesionario.repository.MarcaRepository;
import com.grupo4.webapp.concesionario.util.ConcesionarioAlert;
import com.grupo4.webapp.concesionario.util.Me;

@Service
public class MarcaService implements IMarcaService{

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private ConcesionarioAlert concesionarioAlert;

    @Override
    public List<Marca> listarMarcas() {
        return marcaRepository.findAll();
    }

    @Override
    public Marca buscarMarcaPorId(Long id) {
        return marcaRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean guardarMarca(Marca marca, MethodType methodType) {
        if(methodType == MethodType.POST){
            if(!verificarMarcaDuplicada(marca)){
                concesionarioAlert.mostrarAlertaInfo(401);
                 return marcaRepository.save(marca);;
            }else{
                concesionarioAlert.mostrarAlertaInfo(500);
            }
    }else if(methodType == MethodType.PUT){
        try {
          if(concesionarioAlert.mostrarAlertaConfirmacion(106).get() == ButtonType.OK) {
            if(!verificarMarcaDuplicada(marca)){
                concesionarioAlert.mostrarAlertaInfo(401);
                return marcaRepository.save(marca);

           }else{
               concesionarioAlert.mostrarAlertaInfo(500);
           }
          }  
        } catch (Exception e) {
            concesionarioAlert.mostrarAlertaInfo(404);
        }
    }
       return false;
    }

    @Override
    public void eliminarMarca(Marca marca) {
        try {
            if(concesionarioAlert.mostrarAlertaConfirmacion(405).get() == ButtonType.OK){
                marcaRepository.delete(marca);
                concesionarioAlert.mostrarAlertaInfo(401);
            }
        } catch (Exception e) {
            concesionarioAlert.mostrarAlertaInfo(404);
        }
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
