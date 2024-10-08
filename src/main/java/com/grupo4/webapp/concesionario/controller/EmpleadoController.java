package com.grupo4.webapp.concesionario.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grupo4.webapp.concesionario.model.Empleado;
import com.grupo4.webapp.concesionario.service.EmpleadoService;
import com.grupo4.webapp.concesionario.util.MethodType;

@Controller
@RestController
@RequestMapping(value = "")
public class EmpleadoController {
    @Autowired
    EmpleadoService empleadoService;

    @GetMapping("/empleados")
    public List<Empleado> listarEmpleado() {
        return empleadoService.listarEmpleados();
    }

    @GetMapping("/empleado")
    public ResponseEntity<Empleado> buscarEmpleadoPorId(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(empleadoService.buscarEmpleadoPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/empleado")
    public ResponseEntity<Map<String, String>> guardarEmpleado(@RequestBody Empleado empleado) {
        Map<String, String> response = new HashMap<>();
        try {
            empleadoService.guardarEmpleado(empleado, MethodType.POST);
            response.put("message", "Empleado agregado con éxito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "error al agregar el empleado");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/empleado")
    public ResponseEntity<Map<String, String>> editarEmpleado(@RequestParam Long id,
            @RequestBody Empleado empleadoNuevo) {
        Map<String, String> response = new HashMap<>();
        try {
            Empleado empleado = empleadoService.buscarEmpleadoPorId(id);
            empleado.setNombre(empleadoNuevo.getNombre());
            empleado.setApellido(empleadoNuevo.getApellido());
            empleado.setTelefono(empleadoNuevo.getTelefono());
            empleado.setDireccion(empleadoNuevo.getDireccion());
            empleado.setDpi(empleadoNuevo.getDpi());
            empleadoService.guardarEmpleado(empleado, MethodType.PUT);
            response.put("message", "empleado editado con exito");
            return ResponseEntity.ok(response);


        } catch (Exception e) {
            response.put("error", "no se pudo editar el empleado");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/empleado")
    public ResponseEntity<Map<String, String>> eliminarEmpleado(@RequestParam Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            Empleado empleado = empleadoService.buscarEmpleadoPorId(id);
            empleadoService.eliminarEmpleado(empleado);
            response.put("message", "empleado eliminado con exito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "no se pudo eliminar el Empleado");
            return ResponseEntity.badRequest().body(response);
        }

    }
}
