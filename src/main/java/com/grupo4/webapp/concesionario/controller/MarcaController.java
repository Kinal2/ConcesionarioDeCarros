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

import com.grupo4.webapp.concesionario.model.Marca;
import com.grupo4.webapp.concesionario.service.MarcaService;

@Controller
@RestController
@RequestMapping(value ="")
public class MarcaController {

    @Autowired
    MarcaService marcaService;

    @GetMapping("/marcas")
    public List<Marca> listarMarcas(){
        return marcaService.listarMarcas();
    }

    @GetMapping("/marca")
    public ResponseEntity<Marca> buscarMarcaPorId(@RequestParam Long id){
        try {
            return ResponseEntity.ok(marcaService.buscarMarcaPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/categoria")
    public ResponseEntity<Map<String, String>> agregarMarca(@RequestBody Marca marca){
        Map<String, String> response = new HashMap<>();
        try {
            if(marcaService.guardarMarca(marca)){
                response.put("message", "Se agrego con exito la marca");
                return ResponseEntity.ok(response);
            }else{
                response.put("message","Marca ya existente");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("Error", "Error al agregar la marca");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/marca")
    public ResponseEntity<Map<String, String>> editarMarca(@RequestParam Long id, @RequestBody Marca nuevaMarca){
        Map<String, String> response = new HashMap<>();
        try {
            Marca marcaOld = marcaService.buscarMarcaPorId(id);
            marcaOld.setNombreMarca(nuevaMarca.getNombreMarca());
            marcaService.guardarMarca(marcaOld);
            response.put("message", "Marca editada con exito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "La marca no se puede editar");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/marca")
    public ResponseEntity<Map<String, String>> eliminarMarca(@RequestParam Long id){
        Map<String, String> response = new HashMap<>();
        try{
            Marca marca = marcaService.buscarMarcaPorId(id);
            marcaService.eliminarMarca(marca);
            response.put("message", "Marca eliminada con exito!");
            return ResponseEntity.ok(response);
        } catch(Exception e) {
            response.put("message", "Error no se elimino la marca");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
