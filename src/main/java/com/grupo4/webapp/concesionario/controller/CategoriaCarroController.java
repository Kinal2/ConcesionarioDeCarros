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

import com.grupo4.webapp.concesionario.model.CategoriaCarro;
import com.grupo4.webapp.concesionario.service.CategoriaCarroService;

@Controller
@RestController
@RequestMapping(value = "")
public class CategoriaCarroController {

    @Autowired
    CategoriaCarroService categoriaCarroService;

    @GetMapping("/categoriaCarros")
    public List<CategoriaCarro> listarCategoriaCarros(){
        return categoriaCarroService.listarCategoriaCarro();
    }

    @GetMapping("/categoriaCarro")
    public ResponseEntity<CategoriaCarro> buscarCategoriaCarroPorId(@RequestParam Long id){
        try {
            return ResponseEntity.ok(categoriaCarroService.buscarCategoriaCarro(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/categoriaCarro")
    public ResponseEntity<Map<String, Boolean>> agregarCategoriaCarro(@RequestBody CategoriaCarro categoriaCarro){
        Map<String, Boolean> response = new HashMap<>();
        try {
            if (categoriaCarroService.guardarCategoriaCarros(categoriaCarro)) {
                response.put("Se agrego la Categoria del Carro con exito", Boolean.TRUE);
                return ResponseEntity.ok(response);
            } else {
                response.put("Err", Boolean.FALSE);
                response.put("No se pudo agregar la Categoria porq se duplicaria", Boolean.FALSE);
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (Exception e) {
            response.put("Err", Boolean.FALSE);
            response.put("No se pudo agregar la Categoria del carro", Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/categoriaCarro")
    public ResponseEntity<Map<String, Boolean>> editarCliente(@RequestParam Long id, @RequestBody CategoriaCarro categoriaCarroNuevo){
        Map<String, Boolean> response = new HashMap<>();
        try{
            CategoriaCarro categoriaCarroViejo = categoriaCarroService.buscarCategoriaCarro(id);
            categoriaCarroViejo.setNombreCategoriaCarro(categoriaCarroNuevo.getNombreCategoriaCarro());
            categoriaCarroViejo.setDescripcionCategoriaCarro(categoriaCarroNuevo.getDescripcionCategoriaCarro());
            if (categoriaCarroService.guardarCategoriaCarros(categoriaCarroViejo)) {
                response.put("Se edito con exito la categoria del Carro", Boolean.TRUE);
                return ResponseEntity.ok(response);
            } else {
                response.put("Err", Boolean.FALSE);
                response.put("no se pudo editar la Categoria porq ya hay una categoria igual existente", Boolean.FALSE);
                return ResponseEntity.badRequest().body(response);
            }
            
        }catch(Exception e){
            response.put("Err", Boolean.FALSE);
            response.put("no se pudo editar la Categoria", Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/categoriaCarro")
    public ResponseEntity<Map<String, Boolean>> eliminarCategoriaCarroPorId(@RequestParam Long id){
        Map<String, Boolean> response = new HashMap<>();
        try {
            CategoriaCarro categoriaCarro = categoriaCarroService.buscarCategoriaCarro(id);
            categoriaCarroService.eliminarCategoriaCarros(categoriaCarro);
            response.put("Se elimino esta Categoria de Carros", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("Err", Boolean.FALSE);
            response.put("No se pudo eliminar la Categoria de estod Carros", Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }
    }
}
