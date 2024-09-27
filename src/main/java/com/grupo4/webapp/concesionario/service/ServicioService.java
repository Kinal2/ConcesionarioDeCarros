package com.grupo4.webapp.concesionario.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import com.grupo4.webapp.concesionario.model.Carro;
import com.grupo4.webapp.concesionario.model.Servicio;
import com.grupo4.webapp.concesionario.repository.ServicioRepository;
import com.grupo4.webapp.concesionario.util.ConcesionarioAlert;
import com.grupo4.webapp.concesionario.util.EstadoCarro;
import com.grupo4.webapp.concesionario.util.MethodType;

import javafx.scene.control.ButtonType;

@Service
public class ServicioService implements IServicioService {

    @Autowired
    ServicioRepository servicioRepository;

    @Autowired
    CarroService carroService;

    @Autowired
    ConcesionarioAlert concesionarioAlert;

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
            concesionarioAlert.mostrarAlertaInfo(401);
            return servicioRepository.save(servicio);

        } else if (methodType == MethodType.PUT) {
            try {
                if (concesionarioAlert.mostrarAlertaConfirmacion(106).get() == ButtonType.OK) {
                    if (servicio.getCompletado()) {
                        servicio.setFechadeSalida(Date.valueOf(LocalDate.now()));
                        cambiarEstadoCarro(servicio.getCarros(), EstadoCarro.DISPONIBLE);
                    }
                    concesionarioAlert.mostrarAlertaInfo(401);
                    return servicioRepository.save(servicio);
                }
            } catch (Exception e) {
                concesionarioAlert.mostrarAlertaInfo(404);
            }

        }
        return servicio;

    }

    @Override
    public void eliminarServicio(Servicio servicio) {
        try {
            if (concesionarioAlert.mostrarAlertaConfirmacion(405).get() == ButtonType.OK) {
                cambiarEstadoCarro(servicio.getCarros(), EstadoCarro.DISPONIBLE);
                servicioRepository.delete(servicio);
                concesionarioAlert.mostrarAlertaInfo(401);
            }
        } catch (Exception e) {
            concesionarioAlert.mostrarAlertaInfo(404);
        }

    }

    @Override
    public void carrosCompletados(Servicio servicio, Servicio newServicio) {
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
