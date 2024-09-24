package com.grupo4.webapp.concesionario.model;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "Servicios")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date fechadeEntrada;
    private Date fechadeSalida;
    @NotBlank
    private String descripcion;
    private Boolean completado;
    @NotNull
    private Double costo;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "servicio_carros",
    joinColumns = @JoinColumn(name = "servicio_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "carros_id", referencedColumnName = "id"))
    private List<Carro> carros;

    public String formatoCarros() {
        return carros.stream().map(Carro::getModelo).collect(Collectors.joining(", ")); 
    }
}
