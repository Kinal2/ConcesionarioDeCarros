package com.grupo4.webapp.concesionario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.grupo4.webapp.concesionario.model.Venta;
import com.grupo4.webapp.concesionario.repository.VentaRepository;

public class VentaService implements IVentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Override
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta buscarVentaPorId(Long id) {
        return ventaRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean guardarVenta(Venta venta) {
        if(!verificarEstadoCarro(venta)){
            ventaRepository.save(venta);
            return true;
        }
        return false;
    }

    @Override
    public void eliminarVenta(Venta venta) {
        ventaRepository.delete(venta);
    }

    @Override
    public Boolean verificarEstadoCarro(Venta ventaNueva) {
        Boolean flag = false;
        if(ventaNueva.getCarro().getEstado().trim().equalsIgnoreCase("En Mantenimiento") || ventaNueva.getCarro().getEstado().trim().equalsIgnoreCase("Vendido") ){
            flag = true;
        }
        return flag;
    }

}
