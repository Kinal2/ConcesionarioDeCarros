package com.grupo4.webapp.concesionario.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "Categorias")
public class CategoriaCarro {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String nombreCategoriaCarro;
    @NotBlank
    private String descripcionCategoriaCarro;

    @Override
    public String toString() {
        return nombreCategoriaCarro;
    }
    
}
