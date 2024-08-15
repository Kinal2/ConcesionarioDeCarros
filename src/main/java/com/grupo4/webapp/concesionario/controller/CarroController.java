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

import com.grupo4.webapp.concesionario.model.Carro;
import com.grupo4.webapp.concesionario.service.CarroService;

@Controller
@RestController
@RequestMapping(value = "")
public class CarroController {

    @Autowired
    CarroService carroService;

    @GetMapping("/carros")
    public ResponseEntity<List<Carro>> listarCarro(){
        try {
            return ResponseEntity.ok(carroService.listarCarro());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/carro")
    public ResponseEntity<Carro> buscarCarroPorId(@RequestParam Long id){
        try {
            return ResponseEntity.ok(carroService.buscarCarroPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/carro")
    public ResponseEntity<Map<String, String>> agregarCarro(@RequestBody Carro carro){
        Map<String, String> response = new HashMap<>();
        try {
            carroService.guardarCarro(carro);
            response.put("message", "El carro se ha creado con exito!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("messgae", "Error");
            response.put("err", "Hubo un error al agregar un carro");
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/carro")
    public ResponseEntity<Map<String, String>> editarCarro(@RequestParam Long id, @RequestBody Carro carroNuevo){
        Map<String, String> response = new HashMap<>();
        try {
            Carro carro = carroService.buscarCarroPorId(id);
            carro.setModelo(carroNuevo.getModelo());
            carro.setAño(carroNuevo.getAño());
            carro.setPrecio(carroNuevo.getPrecio());
            carro.setColor(carroNuevo.getColor());
            carro.setKilometraje(carroNuevo.getKilometraje());
            carro.setEstado(carroNuevo.getEstado());
            carro.setMarca(carroNuevo.getMarca());
            carro.setCategoria(carroNuevo.getCategoria());
            carroService.guardarCarro(carro);
            response.put("message", "El carro se ha editado con exito!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "El carro no se pudo editar!");
            return ResponseEntity.badRequest().body(response);
            
        }
    }

    @DeleteMapping("/carro")
    public ResponseEntity<Map<String, String>> eliminarCarro(@RequestParam Long id){
        Map<String, String> response = new HashMap<>();
        try {
            Carro carro = carroService.buscarCarroPorId(id);
            carroService.eliminarCarro(carro);
            response.put("message", "El carro se ha eliminado con exito!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "El carro no se ha podido eliminar!");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
