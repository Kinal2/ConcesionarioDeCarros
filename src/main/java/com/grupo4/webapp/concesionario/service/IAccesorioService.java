package com.grupo4.webapp.concesionario.service;

import java.util.List;


import com.grupo4.webapp.concesionario.model.Accesorio;


public interface IAccesorioService {

    public List<Accesorio> listarAccesorios();

    public Accesorio buscaAccesorioPorId(Long id);

    public Accesorio guardarAccesorio(Accesorio accesorio);

    public void eliminarAccesorio(Accesorio accesorio);

    public void restarStock(Accesorio accesorio);
}
