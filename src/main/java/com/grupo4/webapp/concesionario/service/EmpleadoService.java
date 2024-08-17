package com.grupo4.webapp.concesionario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo4.webapp.concesionario.model.Empleado;
import com.grupo4.webapp.concesionario.repository.EmpleadoRepositoy;
import com.grupo4.webapp.concesionario.util.MethodType;

@Service
public class EmpleadoService implements IEmpleadoService{
    @Autowired
    private EmpleadoRepositoy empleadoRepositoy;

    @Override
    public List<Empleado> listarEmpleados(){
        return empleadoRepositoy.findAll();
    }

    @Override
    public Empleado buscarEmpleadoPorId(Long id){
        return empleadoRepositoy.findById(id).orElse(null);
    }

    @Override
    public Boolean guardarEmpleado(Empleado empleado, MethodType methodType) {
        if(methodType.equals(MethodType.POST)){
            if(!verificarDpiDuplicado(empleado)){
                empleadoRepositoy.save(empleado);
                return true;
            }else{
                return false;
            }
        }else if (methodType.equals(MethodType.PUT)) {
            empleadoRepositoy.save(empleado);
            return true; 
        }
        return true;
    }

    @Override
    public void eliminarEmpleado(Empleado empleado){
        empleadoRepositoy.delete(empleado);
    }

    @Override
    public Boolean verificarDpiDuplicado(Empleado empleadoNuevo) {
        List<Empleado> empleados = listarEmpleados();
        Boolean flag = false;
        for (Empleado empleado2 : empleados) {
            if(empleado2.getDpi().equals(empleadoNuevo.getDpi()) && !empleado2.getId().equals(empleadoNuevo.getId())){
                flag = true;
            }
        }
        return flag;
    }

}
