package com.grupo4.webapp.concesionario.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "Clientes")
public class Cliente {
    @Id
    @NotNull(message = "dpi no puede se nulo")
    private Long dpi;
    @NotNull(message = "nombre no puede ser nulo")
    private String nombre;
    @NotNull(message = "apellido no puede ser nulo")
    private String apellido;
    @NotNull(message = "direccion no puede se nulo")
    private String direccion;
    @NotNull(message = "telefono no puede se nulo")
    private String telefono;
}