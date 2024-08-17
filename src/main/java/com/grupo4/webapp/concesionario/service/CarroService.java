package com.grupo4.webapp.concesionario.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo4.webapp.concesionario.model.Accesorio;
import com.grupo4.webapp.concesionario.model.Carro;
import com.grupo4.webapp.concesionario.repository.CarroRepository;
import com.grupo4.webapp.concesionario.util.EstadoCarro;
import com.grupo4.webapp.concesionario.util.MethodType;

@Service
public class CarroService implements ICarroService{

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private AccesorioService accesorioService;

    @Override
    public List<Carro> listarCarro() {
        return carroRepository.findAll();
    }

    @Override
    public Carro buscarCarroPorId(Long id) {
        return carroRepository.findById(id).orElse(null);
    }

    @Override
    public Carro guardarCarro(Carro carro, MethodType methodType) {
        if(methodType == MethodType.POST){
            carro.setEstado(EstadoCarro.DISPONIBLE);
            agregarAccesorioACarro(carro);
            return carroRepository.save(carro);
        }else{
            agregarAccesorioACarro(carro);
            return carroRepository.save(carro);
            
        }
        
    }

    @Override
    public void eliminarCarro(Carro carro) {
        carroRepository.delete(carro);
    }

    @Override
    public void cambiarEstadoCarro(Carro carro, EstadoCarro estado) {
        carro.setEstado(estado);
        carroRepository.save(carro);
    }

    @Override
    public void agregarAccesorioACarro(Carro carro) {
        for (Accesorio accesorio: carro.getAccesorios()) {
            Accesorio accesorioCompleto = accesorioService.buscaAccesorioPorId(accesorio.getId());
            accesorioService.restarStock(accesorioCompleto);
        }
    }

}
