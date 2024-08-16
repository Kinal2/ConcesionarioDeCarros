package com.grupo4.webapp.concesionario.service;

import java.util.List;

import com.grupo4.webapp.concesionario.model.Carro;
import com.grupo4.webapp.concesionario.model.Servicio;
import com.grupo4.webapp.concesionario.util.EstadoCarro;
import com.grupo4.webapp.concesionario.util.MethodType;

public interface IServicioService {
    public List<Servicio> listaServicios();

    public Servicio buscarServicioPorId(Long id);

    public Servicio guardarServicio(Servicio servicio ,MethodType methodType);

    public void eliminarServicio(Servicio servicio);

    public void cambiarEstadoCarro(List<Carro> carros, EstadoCarro estado);
}
