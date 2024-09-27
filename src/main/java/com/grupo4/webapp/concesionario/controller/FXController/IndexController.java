package com.grupo4.webapp.concesionario.controller.FXController;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.grupo4.webapp.concesionario.system.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import lombok.Setter;

@Component
public class IndexController implements Initializable {

    @FXML
    MenuItem btnCategorias, btnClientes, btnEmpleados, btnCarros,btnMarcas, btnAccesorios, btnServicios, btnVentas;

    @Setter
    private Main stage;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    @FXML
    public void handleButtonAction(ActionEvent event) throws Exception {
        if (event.getSource() == btnCategorias) {
            stage.categoriaCarroView();
        }else if(event.getSource() == btnClientes){
            stage.clienteView();
        }else if(event.getSource() == btnEmpleados){
            stage.empleadoView();
        }else if(event.getSource() == btnCarros){
            stage.carroView();
        }else if(event.getSource() == btnServicios){
            stage.servicioView();
        }else if(event.getSource() == btnMarcas){
            stage.marcaView();
        }else if(event.getSource() == btnAccesorios){
            stage.accesoriosView();
        }else if(event.getSource() == btnVentas){
            stage.ventaView();
        }
    }
}