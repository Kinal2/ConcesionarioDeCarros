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
import com.grupo4.webapp.concesionario.util.EstadoCarro;
import com.grupo4.webapp.concesionario.util.MethodType;

@Service
public class VentaService implements IVentaService {

    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private CarroService carroService;

    @Override
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta buscarVentaPorId(Long id) {
        return ventaRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean guardarVenta(Venta venta, MethodType methodType) {
        if(methodType == MethodType.POST){
            if(!verificarEstadoCarro(venta)){
                Carro carro = carroService.buscarCarroPorId(venta.getCarro().getId());
                carroService.cambiarEstadoCarro(carro, EstadoCarro.VENDIDO);
                venta.setFecha(Date.valueOf(LocalDate.now()));
                venta.setPrecioFinal(calcularPrecioFinalVenta(venta));
                ventaRepository.save(venta);
                return true;
            }else{
                return false;
            }
        }else if( methodType == MethodType.PUT){
            ventaRepository.save(venta);
            return true;
        }else{
            return false;
        }
        
        
    }

    @Override
    public void eliminarVenta(Venta venta) {
        ventaRepository.delete(venta);
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
