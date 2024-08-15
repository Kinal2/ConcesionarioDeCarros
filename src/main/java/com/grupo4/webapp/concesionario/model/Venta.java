package com.grupo4.webapp.concesionario.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "Ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "La fecha no puede ser nula")
    private Date fecha;
    @NotNull(message = "El precio final no puede ser nulo")
    private Double precioFinal;
    @ManyToOne
    private Carro carro;
    @ManyToOne
    private Cliente cliente;
    @ManyToOne
    private Empleado empleado;
    

}
