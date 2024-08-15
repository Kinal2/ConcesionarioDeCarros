package com.grupo4.webapp.concesionario.service;

import java.util.List;

import com.grupo4.webapp.concesionario.model.Venta;

public interface IVentaService {

    public List<Venta> listarVentas();

    public Venta buscarVentaPorId(Long id);

    public Boolean guardarVenta(Venta venta);

    public void eliminarVenta(Venta venta);

    public Boolean verificarEstadoCarro(Venta ventaNueva);

}
