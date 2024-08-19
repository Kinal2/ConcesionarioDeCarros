package com.grupo4.webapp.concesionario.model;

import com.grupo4.webapp.concesionario.util.EstadoCarro;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "Carro")
public class Carro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String modelo;
    private int año;
    private Double precio;
    private String color;
    private int kilometraje;
    @Enumerated(EnumType.STRING)
    private EstadoCarro estado;
    @ManyToOne(fetch = FetchType.EAGER)
    private Marca marca;
    @ManyToOne(fetch = FetchType.EAGER)
    private CategoriaCarro categoria; 
}