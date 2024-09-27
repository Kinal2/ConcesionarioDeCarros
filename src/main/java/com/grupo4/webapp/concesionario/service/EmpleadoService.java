package com.grupo4.webapp.concesionario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.grupo4.webapp.concesionario.model.Empleado;
import com.grupo4.webapp.concesionario.repository.EmpleadoRepositoy;
import com.grupo4.webapp.concesionario.util.ConcesionarioAlert;
import com.grupo4.webapp.concesionario.util.MethodType;

import javafx.scene.control.ButtonType;

@Service
public class EmpleadoService implements IEmpleadoService {
    @Autowired
    EmpleadoRepositoy empleadoRepositoy;

    @Autowired
    ConcesionarioAlert concesionarioAlert;

    Boolean flag = false;
    @Override
    public List<Empleado> listarEmpleados() {
        return empleadoRepositoy.findAll();
    }

    @Override
    public Empleado buscarEmpleadoPorId(Long id) {
        return empleadoRepositoy.findById(id).orElse(null);
    }

    @Override
    public Empleado guardarEmpleado(Empleado empleado, MethodType methodType) {
        if (methodType.equals(MethodType.POST)) {
            if (verificarDpiDuplicado(empleado)) {
                concesionarioAlert.mostrarAlertaInfo(406);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El DPI ya pertenece a otro empleado");
            }
            concesionarioAlert.mostrarAlertaInfo(401);
            return empleadoRepositoy.save(empleado);

        } else if (methodType.equals(MethodType.PUT)) {
            Empleado empleadoExistente = buscarEmpleadoPorId(empleado.getId());
            if (empleadoExistente == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El empleado no existe");
            }
            if (!empleadoExistente.getDpi().equals(empleado.getDpi()) && verificarDpiDuplicado(empleado)) {
                concesionarioAlert.mostrarAlertaInfo(406);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El DPI ya pertenece a otro empleado");
            }
            try {
                if (concesionarioAlert.mostrarAlertaConfirmacion(106).get() == ButtonType.OK) {
                    concesionarioAlert.mostrarAlertaInfo(401);
                    return empleadoRepositoy.save(empleado);
                }
            } catch (Exception e) {
                concesionarioAlert.mostrarAlertaInfo(404);
            }
            return empleado;
        }
        return null;
    }


    @Override
    public void eliminarEmpleado(Empleado empleado) {
        if (concesionarioAlert.mostrarAlertaConfirmacion(405).get() == ButtonType.OK) {
            empleadoRepositoy.delete(empleado);
            concesionarioAlert.mostrarAlertaInfo(401);
        }
    }

    @Override
public Boolean verificarDpiDuplicado(Empleado empleadoNuevo) {
    flag = false;  
    List<Empleado> empleados = listarEmpleados();
    for (Empleado empleado2 : empleados) {
        if (empleado2.getDpi().equals(empleadoNuevo.getDpi()) && !empleado2.getId().equals(empleadoNuevo.getId())) {
            flag = true;
            break; 
        }
    }
    return flag;
}


}
