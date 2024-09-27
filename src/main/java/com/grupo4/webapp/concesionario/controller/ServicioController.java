package com.grupo4.webapp.concesionario.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.grupo4.webapp.concesionario.model.Servicio;
import com.grupo4.webapp.concesionario.service.ServicioService;
import com.grupo4.webapp.concesionario.util.MethodType;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping(value = "")
public class ServicioController {
    @Autowired
    ServicioService servicioService;

    @GetMapping("/servicios")
    public List<Servicio> listaServicios() {
        return servicioService.listaServicios();
    }

    @GetMapping("/servicio")
    public ResponseEntity<Servicio> buscarServicioPorId(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(servicioService.buscarServicioPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/servicio")
    public ResponseEntity<Map<String, String>> guardarServicio(@RequestBody Servicio servicio) {
        Map<String, String> response = new HashMap<>();
        try {
            servicioService.guardarServicio(servicio, MethodType.POST);
            response.put("message", "Servicio agregado con éxito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("err", "El servicio no se pudo agregar");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/servicio")
    public ResponseEntity<Map<String,String>> editarServicio(@RequestParam Long id, @RequestBody Servicio newServicio) {
        Map<String, String> response = new HashMap<>();
        try {
            Servicio servicio = servicioService.buscarServicioPorId(id);
            servicio.setDescripcion(newServicio.getDescripcion());
            servicio.setCompletado(newServicio.getCompletado());
            servicio.setCosto(newServicio.getCosto());
            servicioService.carrosCompletados(servicio, newServicio);
            servicio.setCarros(newServicio.getCarros());
            servicioService.guardarServicio(servicio, MethodType.PUT);
            response.put("message", "Servicio editado con éxito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("err", "El servicio no se pudo editar");
            return ResponseEntity.badRequest().body(response);
        }
        
    }

    @DeleteMapping("/servicio")
    public ResponseEntity<Map<String,String>> eliminarServicio(@RequestParam Long id){
        Map<String,String> response = new HashMap<>();
        try {
            Servicio servicio = servicioService.buscarServicioPorId(id);
            servicioService.eliminarServicio(servicio);
            response.put("message", "Servicio Eliminado con éxito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("err", "No se pudo eliminar el Servicio");
            return ResponseEntity.badRequest().body(response);
        }
    }

}
