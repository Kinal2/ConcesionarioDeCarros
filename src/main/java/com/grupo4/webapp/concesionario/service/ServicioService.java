package com.grupo4.webapp.concesionario.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo4.webapp.concesionario.model.Carro;
import com.grupo4.webapp.concesionario.model.Servicio;
import com.grupo4.webapp.concesionario.repository.ServicioRepository;
import com.grupo4.webapp.concesionario.util.EstadoCarro;
import com.grupo4.webapp.concesionario.util.MethodType;

@Service
public class ServicioService implements IServicioService {

    @Autowired
    ServicioRepository servicioRepository;

    @Autowired
    CarroService carroService;

    @Override
    public List<Servicio> listaServicios() {
        return servicioRepository.findAll();
    }

    @Override
    public Servicio buscarServicioPorId(Long id) {
        return servicioRepository.findById(id).orElse(null);
    }

    @Override
    public Servicio guardarServicio(Servicio servicio, MethodType methodType) {
        if (methodType == MethodType.POST) {
            servicio.setCompletado(false);
            servicio.setFechadeEntrada(Date.valueOf(LocalDate.now()));
            servicio.setFechadeSalida(Date.valueOf(LocalDate.now().plusDays(5)));
            cambiarEstadoCarro(servicio.getCarros(), EstadoCarro.EN_SERVICIO);
            return servicioRepository.save(servicio);
            
        }else if(methodType == MethodType.PUT){
            if(servicio.getCompletado()){
                servicio.setFechadeSalida(Date.valueOf(LocalDate.now()));
                cambiarEstadoCarro(servicio.getCarros(), EstadoCarro.DISPONIBLE);
            }
            return servicioRepository.save(servicio);
        }else{
            return null;
        }

    }

    @Override
    public void eliminarServicio(Servicio servicio) {
        cambiarEstadoCarro(servicio.getCarros(), EstadoCarro.DISPONIBLE);
        servicioRepository.delete(servicio);
        
    }

    @Override
    public void carrosCompletados(Servicio servicio, Servicio newServicio){
        List<Carro> carrosCompleatos = new ArrayList<>();
        for (Carro carro : servicio.getCarros()) {
            Carro carroCompleto = carroService.buscarCarroPorId(carro.getId());
            if (!newServicio.getCarros().contains(carroCompleto)) {
                carrosCompleatos.add(carroCompleto);
            }
        }
        cambiarEstadoCarro(carrosCompleatos, EstadoCarro.DISPONIBLE);
        cambiarEstadoCarro(newServicio.getCarros(), EstadoCarro.EN_SERVICIO);
    }

    @Override
    public void cambiarEstadoCarro(List<Carro> carros, EstadoCarro estado) {
        for (Carro carro : carros) {
            Carro carroCompleto = carroService.buscarCarroPorId(carro.getId());
            carroService.cambiarEstadoCarro(carroCompleto, estado);
        }
    }


}
