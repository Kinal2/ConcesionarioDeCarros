package com.grupo4.webapp.concesionario.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity 
@Data
@ToString
@Table(name = "Accesorios")
public class Accesorio {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id; 
    private String nombreAccesorio;
    private String descripcionAccesorio;
    private Double precioAccesorio;
    private Integer stock;
}
