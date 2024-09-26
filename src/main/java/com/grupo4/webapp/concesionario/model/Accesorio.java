package com.grupo4.webapp.concesionario.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
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
    @NotBlank
    private String nombreAccesorio;
    @NotBlank
    private String descripcionAccesorio;
    @NotNull
    @Positive
    private Double precioAccesorio;
    @NotNull
    @PositiveOrZero
    private Integer stock;  
}
