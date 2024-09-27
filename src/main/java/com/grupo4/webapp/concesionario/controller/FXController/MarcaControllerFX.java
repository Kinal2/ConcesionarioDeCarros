package com.grupo4.webapp.concesionario.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grupo4.webapp.concesionario.model.Marca;
import com.grupo4.webapp.concesionario.service.MarcaService;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Setter;

@Component
public class MarcaControllerFX implements Initializable {

    @FXML
    TextField tfId, tfNombreMarca, tfBuscar;
    @FXML
    Button btnGuardar,btnRegresar,btnBuscar,btnEliminar, btnLimpiar;
    @FXML
    TableView tblMarcas;
    @FXML
    TableColumn colIdMarca, colNombreMarca;

    @Setter
    private Main stage;

    @Autowired
    MarcaService marcaService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       cargarDatos();
    }

    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(tfId.getText().isBlank()){
                agregarMarca();
            }else{
                editarMarca();
            }
        }else if(event.getSource() == btnRegresar){
            stage.indexView();
        }else if(event.getSource() == btnEliminar){
            eliminarMarca();
        }else if(event.getSource() == btnLimpiar){
            limpiarForm();
        }else if(event.getSource() == btnBuscar){
            tblMarcas.getItems().clear();
            if(tfBuscar.getText().isBlank()){
                cargarDatos();
            }else{
                tblMarcas.getItems().add(buscarMarca());
                colIdMarca.setCellValueFactory(new PropertyValueFactory<Marca,Long>("id"));
                colNombreMarca.setCellValueFactory(new PropertyValueFactory<Marca,String>("nombreMarca"));
            }
        }
    }

    public ObservableList<Marca> listaMarcas(){
        return FXCollections.observableList(marcaService.listarMarcas());
    }

    public void cargarDatos(){
        tblMarcas.setItems(listaMarcas());
        colIdMarca.setCellValueFactory(new PropertyValueFactory<Marca,Long>("id"));
        colNombreMarca.setCellValueFactory(new PropertyValueFactory<Marca,String>("nombreMarca"));
    }

    public void cargarFormEditar(){
        Marca marca = (Marca)tblMarcas.getSelectionModel().getSelectedItem();
        tfId.setText(Long.toString(marca.getId()));
        tfNombreMarca.setText(marca.getNombreMarca());
    }

    public void limpiarForm(){
        tfId.clear();
        tfNombreMarca.clear();
    }

    public void agregarMarca(){
        Marca marca = new Marca();
        marca.setNombreMarca(tfNombreMarca.getText());
        marcaService.guardarMarca(marca,MethodType.POST);
        cargarDatos();
    }

    public void editarMarca(){
        Marca marca = marcaService.buscarMarcaPorId(Long.parseLong(tfId.getText()));
        marca.setNombreMarca(tfNombreMarca.getText());
        marcaService.guardarMarca(marca,MethodType.PUT);
        cargarDatos();
    }

    public void eliminarMarca(){
        Marca marca = marcaService.buscarMarcaPorId(Long.parseLong(tfId.getText()));
        marcaService.eliminarMarca(marca);
        cargarDatos();
    }

    public Marca buscarMarca(){
        return marcaService.buscarMarcaPorId(Long.parseLong(tfBuscar.getText()));
    }

}
