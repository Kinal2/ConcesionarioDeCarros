package com.grupo4.webapp.concesionario.model;

import java.util.List;
import com.grupo4.webapp.concesionario.util.EstadoCarro;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "Carros")
public class Carro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "ingrese el modelo")
    private String modelo;
    @NotNull
    private int año;
    @NotNull
    @Positive
    private Double precio;
    @NotBlank(message = "ingrese un color")
    private String color;
    @NotNull
    @Positive
    private int kilometraje;
    @Enumerated(EnumType.STRING)
    private EstadoCarro estado;
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Marca marca;
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private CategoriaCarro categoria; 
    @ManyToMany
    @JoinTable(name = "carro_accesorios",
    joinColumns = @JoinColumn(name = "carro_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "accesorios_id",referencedColumnName = "id"))
    List<Accesorio> accesorios;
}

