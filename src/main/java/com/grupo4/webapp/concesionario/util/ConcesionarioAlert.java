package com.grupo4.webapp.concesionario.util;

import java.util.Optional;

import org.springframework.stereotype.Component;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

@Component
public class ConcesionarioAlert {
    public void mostrarAlertaInfo(int code) {
        Alert alert = null;

        switch (code) {
            case 401:
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Éxito");
                alert.setHeaderText("Operación realizada con éxito");
                alert.setContentText("Se ha realizado la operacion exitosamente.");
                break;
            case 404:
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Al eliminar o editar");
                alert.setHeaderText("Error");
                alert.setContentText("No se pueden modificar o eliminar Registros con refencias en otras tablas");
                break;
            case 406:
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error DPI duplicado");
                alert.setHeaderText("Error");
                alert.setContentText("Ya hay un registro con este DPI");
                break;
            case 0:
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Ha ocurrido un error inesperado.");
                break;

            case 500:
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Marca duplicada");
                alert.setHeaderText("Error");
                alert.setContentText("Ya existe una marca con este nombre");
                break;

            case 501:
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Carro No Disponible");
                alert.setHeaderText("Error");
                alert.setContentText("El carro no esta disponible");
                break;
        }

        alert.showAndWait();
    }

    public Optional<ButtonType> mostrarAlertaConfirmacion(int code) {
        Optional<ButtonType> action = Optional.empty();
        Alert alert;

        switch (code) {
            case 405:
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmar eliminación");
                alert.setHeaderText("Eliminar Registro");
                alert.setContentText("¿Estás seguro de que deseas eliminar el registro?");
                action = alert.showAndWait();
                break;

            case 106:
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmar edición");
                alert.setHeaderText("Editar Registro");
                alert.setContentText("¿Estás seguro de que deseas editar este registro?");
                action = alert.showAndWait();
                break;

            default:
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Acción no permitida");
                alert.setHeaderText("Operación inválida");
                alert.setContentText("No puedes realizar esta operación.");
                alert.showAndWait();
                break;
        }

        return action;
    }

}
