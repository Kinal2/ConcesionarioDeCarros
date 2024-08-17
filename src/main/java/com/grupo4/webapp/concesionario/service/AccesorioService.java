package com.grupo4.webapp.concesionario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo4.webapp.concesionario.model.Accesorio;
import com.grupo4.webapp.concesionario.repository.AccesorioRepository;

@Service 
public class AccesorioService implements IAccesorioService{

    @Autowired 
    private AccesorioRepository accesorioRepository;

    @Override
    public List<Accesorio> listarAccesorios() {
        return accesorioRepository.findAll();
    }

    @Override
    public Accesorio buscaAccesorioPorId(Long id) {
        return accesorioRepository.findById(id).orElse(null);
    }

    @Override
    public Accesorio guardarAccesorio(Accesorio accesorio) {
        return accesorioRepository.save(accesorio);
    }

    @Override
    public void eliminarAccesorio(Accesorio accesorio) {
        accesorioRepository.delete(accesorio);
    }

}
