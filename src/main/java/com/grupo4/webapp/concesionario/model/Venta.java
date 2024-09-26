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
    private Date fecha;
    private Double precioFinal;
    @ManyToOne
    @NotNull
    private Carro carro;
    @ManyToOne
    @NotNull
    private Cliente cliente;
    @ManyToOne
    @NotNull
    private Empleado empleado;
}
