package com.grupo4.webapp.concesionario.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo4.webapp.concesionario.model.Accesorio;
import com.grupo4.webapp.concesionario.model.Carro;
import com.grupo4.webapp.concesionario.model.Venta;
import com.grupo4.webapp.concesionario.repository.VentaRepository;
import com.grupo4.webapp.concesionario.util.ConcesionarioAlert;
import com.grupo4.webapp.concesionario.util.EstadoCarro;
import com.grupo4.webapp.concesionario.util.MethodType;

import javafx.scene.control.ButtonType;

@Service
public class VentaService implements IVentaService {

    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private CarroService carroService;
    @Autowired
    ConcesionarioAlert concesionarioAlert;

    @Override
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta buscarVentaPorId(Long id) {
        return ventaRepository.findById(id).orElse(null);
    }

    @Override
    public Venta guardarVenta(Venta venta, MethodType methodType) {
        if(methodType == MethodType.POST){
            if(!verificarEstadoCarro(venta)){
                Carro carro = carroService.buscarCarroPorId(venta.getCarro().getId());
                carroService.cambiarEstadoCarro(carro, EstadoCarro.VENDIDO);
                venta.setFecha(Date.valueOf(LocalDate.now()));
                venta.setPrecioFinal(calcularPrecioFinalVenta(venta));
                concesionarioAlert.mostrarAlertaInfo(401);
                return ventaRepository.save(venta);
            }else{
                concesionarioAlert.mostrarAlertaInfo(501);
            }
        }else if( methodType == MethodType.PUT){
            try {
                if(concesionarioAlert.mostrarAlertaConfirmacion(106).get() == ButtonType.OK){
                    if(!verificarEstadoCarro(venta)){
                        concesionarioAlert.mostrarAlertaInfo(401);
                        return ventaRepository.save(venta);
                    }else{
                        concesionarioAlert.mostrarAlertaInfo(501);
                    }
                }
            } catch (Exception e) {
                concesionarioAlert.mostrarAlertaInfo(404
                );
            }
        }
        return venta;
        
    }

    @Override
    public void eliminarVenta(Venta venta) {
        try {
            if(concesionarioAlert.mostrarAlertaConfirmacion(405).get() == ButtonType.OK){
                ventaRepository.delete(venta);
                concesionarioAlert.mostrarAlertaInfo(401);
            }
        } catch (Exception e) {
            concesionarioAlert.mostrarAlertaInfo(404);
        }
    }

    private double calcularPrecioFinalVenta(Venta venta) {
        Carro carro = carroService.buscarCarroPorId(venta.getCarro().getId());
        double precioTotal = 0;
        if (carro != null) {
            precioTotal = carro.getPrecio();
            if (carro.getAccesorios() != null) {
                for (Accesorio accesorio : carro.getAccesorios()) {
                    precioTotal += accesorio.getPrecioAccesorio();
                }
            }
        }
        return precioTotal;
    }

    @Override
    public Boolean verificarEstadoCarro(Venta ventaNueva) {
        Carro carro = carroService.buscarCarroPorId(ventaNueva.getCarro().getId());
        EstadoCarro estado = carro.getEstado();
        Boolean flag = false;
        if(estado == EstadoCarro.EN_SERVICIO || estado == EstadoCarro.VENDIDO ){
            flag = true;
        }
        return flag;
    }

}
