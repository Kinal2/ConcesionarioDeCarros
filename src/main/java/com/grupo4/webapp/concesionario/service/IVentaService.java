package com.grupo4.webapp.concesionario.service;


import java.util.List;

import com.grupo4.webapp.concesionario.model.Venta;
import com.grupo4.webapp.concesionario.util.MethodType;

public interface IVentaService {

    public List<Venta> listarVentas();

    public Venta buscarVentaPorId(Long id);

    public Boolean guardarVenta(Venta venta, MethodType methodType );

    public void eliminarVenta(Venta venta);

    public Boolean verificarEstadoCarro(Venta ventaNueva);

}
