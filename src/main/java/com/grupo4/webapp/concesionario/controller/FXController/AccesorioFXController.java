package com.grupo4.webapp.concesionario.controller.FXController;


import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grupo4.webapp.concesionario.model.Accesorio;
import com.grupo4.webapp.concesionario.service.AccesorioService;
import com.grupo4.webapp.concesionario.system.Main;
import com.grupo4.webapp.concesionario.util.MethodType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Setter;


@Component
public class AccesorioFXController implements Initializable{

    @FXML
    TextField tfId, tfNombre, tfDescripcion, tfPrecio, tfStock; 
    @FXML
    Button btnGuardar, btnVaciar, btnEliminar, btnRegresar, btnBuscar; 
    @FXML
    TableView tblAccesorios;
    @FXML
    TableColumn colId, colNombre, colDescripcion, colPrecio, colStock;

    @Setter
    private Main stage;

    @Autowired
    AccesorioService accesorioService;

    @Override
    public void initialize(URL url, ResourceBundle resources){
        cargarDatos();
    }

    public void handleButtonAccion(ActionEvent event){
        if(event.getSource == btnGuardar){
            if(tfId.getText().isBlank()){
                agregarAccesorio();
            }else{
                editarAccesorio();
            }
        }else if(event.getSource() == btnVaciar){
            vaciarForm();
        }else if(event.getSource() == btnEliminar){
            eliminarAccesorio();
        }
    }


    public void cargarDatos(){
        tblAccesorios.setItems(listaAccesorios());
        colId.setCellValueactory(new PropertyValueFactory<Accesorio, Long>("id"));
        colNombre.setCellValueactory(new PropertyValueFactory<Accesorio, String>("nombreAccesorio"));
        colDescripcion.setCellValueactory(new PropertyValueFactory<Accesorio, String>("descripcionAccesorio"));
        colPrecio.setCellValueactory(new PropertyValueFactory<Accesorio, Double>("precioAccesorio"));
        colStock.setCellValueactory(new PropertyValueFactory<Accesorio, Integer>("stock"));
    }

    public void cargarForm(){
        Accesorio accesorio = (Accesorio) tblAccesorios.getSelectionModel().getSelectedItem();
        if(accesorio != null){
            tfId.setText(Long.toString (accesorio.getId()));
        }
    }

    public void vaciarForm() {
        tfId.clear();
        tfNombre.clear();
        tfDescripcion.clear();
        tfPrecio.clear();
        tfStock.clear();
    }


    public ObservableList<Accesorio> listaAccesorios(){
       return FXCollections.observableList(accesorioService.listaAccesorios());
    }

    public void agregarAccesorio(){
        Accesorio Accesorio = new Accesorio();
        Accesorio.setnombreAccesorio(tfNombre.getText());
        Accesorio.setdescripcionAccesorio(tfDescripcion.getText());
        Accesorio.setprecioAccesorio(tfPrecio.getText());
        Accesorio.setstock(tfStock.getText());
        accesorioService.guardarAccesorio(Accesorio);
        cargarDatos();
    }

    public void editarAccesorio(){
        Accesorio accesorio = accesorioService.buscarAccesorioPorId(Long.parseLong (tfId.getText()));
        accesorio.setnombreAccesorio(tfNombre.getText());
        accesorio.setdescripcionAccesorio(tfDescripcion.getText());
        accesorio.setprecioAccesorio(tfPrecio.getText());
        accesorio.setstock(tfStock.getText());
        accesorioService.guardarAccesorio(accesorio);
        cargarDatos();
    }
    
    public void eliminarAccesorio() {
        Accesorio accesorio = accesorioService.buscarAccesorioPorId(Long.parseLong(tfId.getText()));
        accesorioService.eliminarAccesorio(accesorio);
        cargarDatos();
    }
}
