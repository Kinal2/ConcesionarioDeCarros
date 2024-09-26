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

    @GetMapping("/categorias")
    public List<CategoriaCarro> listarCategoriaCarros() {
        return categoriaCarroService.listarCategoriaCarro();
    }

    @GetMapping("/categoria")
    public ResponseEntity<CategoriaCarro> buscarCategoriaCarroPorId(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(categoriaCarroService.buscarCategoriaCarro(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/categoria")
    public ResponseEntity<Map<String, String>> agregarCategoriaCarro(@RequestBody CategoriaCarro categoriaCarro) {
        Map<String, String> response = new HashMap<>();
        try {
            if (categoriaCarroService.guardarCategoriaCarros(categoriaCarro)) {
                response.put("message", "se agrego la categoria con exito");
                return ResponseEntity.ok(response);
            } else {
                response.put("err", "Categoria Duplicada");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("err", "No se pudo agregar la categoria");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/categoria")
    public ResponseEntity<Map<String, String>> editarCliente(@RequestParam Long id,
            @RequestBody CategoriaCarro categoriaCarroNuevo) {
        Map<String, String> response = new HashMap<>();
        try {
            CategoriaCarro categoriaCarroViejo = categoriaCarroService.buscarCategoriaCarro(id);
            categoriaCarroViejo.setNombreCategoriaCarro(categoriaCarroNuevo.getNombreCategoriaCarro());
            categoriaCarroViejo.setDescripcionCategoriaCarro(categoriaCarroNuevo.getDescripcionCategoriaCarro());
            if (categoriaCarroService.guardarCategoriaCarros(categoriaCarroViejo)) {
                response.put("message", "Se edito con exito la categoria del Carro");
                return ResponseEntity.ok(response);
            } else {
                response.put("err", "No se pudo agregar la categoria");
                return ResponseEntity.badRequest().body(response);
            }

        } catch (Exception e) {
            response.put("err", "No se pudo agregar la categoria");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/categoria")
    public ResponseEntity<Map<String, String>> eliminarCategoriaCarroPorId(@RequestParam Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            CategoriaCarro categoriaCarro = categoriaCarroService.buscarCategoriaCarro(id);
            categoriaCarroService.eliminarCategoriaCarros(categoriaCarro);
            response.put("message", "Se elimino esta Categoria de Carros");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("err", "No se pudo agregar la categoria");
                return ResponseEntity.badRequest().body(response);
        }
    }
}
