package com.grupo4.webapp.concesionario.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo4.webapp.concesionario.model.Accesorio;
import com.grupo4.webapp.concesionario.service.AccesorioService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;


@Controller //se encarga de instanciar el controller
@RestController
@RequestMapping(value = "")
public class AccesorioController {

    @Autowired
    AccesorioService accesorioService;

    @GetMapping("/accesorios")
    public List<Accesorio> listaAccesorios(){
        return accesorioService.listarAccesorios();
    }

    @GetMapping("/Accesorio")
    public ResponseEntity<Accesorio> buscaAccesorioPorId(@RequestParam Long id){
        try {
            return ResponseEntity.ok(accesorioService.buscaAccesorioPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


        @PostMapping("/Accesorio")
    public ResponseEntity<Map<String, String>> agregarAccesorio(@RequestBody Accesorio accesorio){
        Map<String, String> response = new HashMap<>();
        try {
            accesorioService.guardarAccesorio(accesorio);
            response.put("message", "Se compro exitosamente");
        return ResponseEntity.ok(response);
        } catch (Exception e) {
           response.put("err", "Hubo un eror en su transaccion"); 
           return ResponseEntity.badRequest().body(response);
        }
    }


    @PutMapping("/Accesorio")
    public ResponseEntity<Map<String, String>> guardarAccesorio(@PathVariable Long id, @RequestBody Accesorio accesorioNuevo){
        Map<String, String> response = new HashMap<>();
        try {
            Accesorio accesorio = accesorioService.buscaAccesorioPorId(id);
            accesorio.setNombreAccesorio(accesorioNuevo.getNombreAccesorio());
            accesorio.setDescripcionAccesorio(accesorioNuevo.getDescripcionAccesorio());
            accesorio.setPrecioAccesorio(accesorioNuevo.getPrecioAccesorio());
            accesorioService.guardarAccesorio(accesorio);
            response.put("message", "Se edito exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "error al editar");
            return ResponseEntity.badRequest().body(response);
        }
    }


    @DeleteMapping("/Accesorio")
    public ResponseEntity<Map<String, String>> eliminarAccesorio(@PathVariable Long id){
        Map<String,String> response = new HashMap<>();
        try {
            Accesorio accesorio = accesorioService.buscaAccesorioPorId(id);
            accesorioService.eliminarAccesorio(accesorio);
            response.put("Message", "Eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "el accesorio no se elimino correctamente");
            return ResponseEntity.badRequest().body(response);
        }
    }

}
