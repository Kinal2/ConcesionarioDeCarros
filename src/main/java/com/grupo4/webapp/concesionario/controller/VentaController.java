package com.grupo4.webapp.concesionario.controller;

import java.util.List;
import java.util.HashMap;
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

import com.grupo4.webapp.concesionario.model.Venta;
import com.grupo4.webapp.concesionario.service.VentaService;
import com.grupo4.webapp.concesionario.util.MethodType;

@Controller
@RestController
@RequestMapping(value = "")
public class VentaController {

    @Autowired
    VentaService ventaService;

    @GetMapping("/ventas")
    public List<Venta> listarVentas(){
        return ventaService.listarVentas();
    }

    @GetMapping("/venta")
    public ResponseEntity<Venta> buscarVentaPorId(@RequestParam Long id){
        try {
            return ResponseEntity.ok(ventaService.buscarVentaPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/venta")
    public ResponseEntity<Map<String, String>> agregarVenta(@RequestBody Venta venta){
        Map<String, String> response = new HashMap<>();
        try {
            ventaService.guardarVenta(venta,MethodType.POST);
            response.put("message","Venta realizada con exito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error al agregar la venta" + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/venta")
    public ResponseEntity<Map<String, String>> editarVenta(@RequestParam Long id, @RequestBody Venta ventaNueva){
        Map<String, String> response = new HashMap<>();
        try{
            Venta ventaOld = ventaService.buscarVentaPorId(id);
            ventaOld.setFecha(ventaNueva.getFecha());
            ventaOld.setPrecioFinal(ventaNueva.getPrecioFinal());
            ventaOld.setCarro(ventaNueva.getCarro());
            ventaOld.setCliente(ventaNueva.getCliente());
            ventaOld.setEmpleado(ventaNueva.getEmpleado());
            ventaService.guardarVenta(ventaOld, MethodType.PUT);
            response.put("message", "Venta editada con exito!");
            return ResponseEntity.ok(response);
        }catch(Exception e){
            response.put("message", "La venta no se puede editar");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/venta")
    public ResponseEntity<Map<String, String>> eliminarVenta(@RequestParam Long id){
        Map<String, String> response = new HashMap<>();
        try {
            Venta venta = ventaService.buscarVentaPorId(id);
            ventaService.eliminarVenta(venta);
            response.put("message", "Venta eliminada con exito!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error no se elimino la venta");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
