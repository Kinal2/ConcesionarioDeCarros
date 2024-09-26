package com.grupo4.webapp.concesionario.service;

import java.util.List;

import com.grupo4.webapp.concesionario.model.Empleado;
import com.grupo4.webapp.concesionario.util.MethodType;

public interface IEmpleadoService {
    public List<Empleado> listarEmpleados();

    public Empleado buscarEmpleadoPorId(Long id);

    public Boolean guardarEmpleado(Empleado empleado, MethodType methodType);

    public void eliminarEmpleado(Empleado empleado);

    public Boolean verificarDpiDuplicado(Empleado empleadoNuevo);

}
