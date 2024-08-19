package com.grupo4.webapp.concesionario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo4.webapp.concesionario.model.Carro;
import com.grupo4.webapp.concesionario.model.Venta;
import com.grupo4.webapp.concesionario.repository.CarroRepository;
import com.grupo4.webapp.concesionario.repository.VentaRepository;
import com.grupo4.webapp.concesionario.util.EstadoCarro;

@Service
public class VentaService implements IVentaService {

    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private CarroRepository carroRepository;

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
            Carro carro = carroRepository.findById(venta.getCarro().getId()).orElse(null);
            carro.setEstado(EstadoCarro.VENDIDO);
            carroRepository.save(carro);
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
        Carro carro = carroRepository.findById(ventaNueva.getCarro().getId()).orElseThrow();
        EstadoCarro estado = carro.getEstado();
        Boolean flag = false;
        if(estado == EstadoCarro.EN_SERVICIO || estado == EstadoCarro.VENDIDO ){
            flag = true;
        }
        return flag;
    }

}
