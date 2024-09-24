package com.grupo4.webapp.concesionario.controller.FXController;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grupo4.webapp.concesionario.model.Servicio;
import com.grupo4.webapp.concesionario.service.ServicioService;
import com.grupo4.webapp.concesionario.system.Main;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import lombok.Setter;


@Component
public class ServicioFXController implements Initializable{

    @Autowired
    ServicioService servicioService;

    @Setter
    private Main stage;
    

    @FXML
    TextField tfId, tfCosto, tfBuscar;

    @FXML
    Button btnGuardar, btnVaciar, btnRegresar, btnEliminar, btnBuscar;

    @FXML
    TableView tblServicios;

    @FXML
    TextArea taDescripcion;

    @FXML
    DatePicker dpEntrada, dpSalida;

    @FXML
    ComboBox cmbCompletado;

    @FXML
    ListView lvCarros;

    @FXML
    TableColumn colId, colEntrada, colSalida, colCompletado, colDescripcion, colCarros,colCosto;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        
    }
    
    public void cargarDatos(){
        tblServicios.setItems(listarServicios());
        colId.setCellValueFactory(new PropertyValueFactory<Servicio, Long>("id"));
        colEntrada.setCellValueFactory(new PropertyValueFactory<Servicio, Date>("fechadeEntrada"));
        colSalida.setCellValueFactory(new PropertyValueFactory<Servicio, Date>("fechadeSalidad"));
        colCompletado.setCellValueFactory(new PropertyValueFactory<Servicio, Boolean>("completado"));
        colCosto.setCellValueFactory(new PropertyValueFactory<Servicio, Double>("costo"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Servicio, String>("descripcion"));
        colCarros.setCellValueFactory(
                        new Callback<TableColumn.CellDataFeatures<Servicio, String>, ObservableValue<String>>() {
                            @Override
                            public ObservableValue<String> call(
                                    TableColumn.CellDataFeatures<Servicio, String> cellData) {
                                Servicio servicio = cellData.getValue();
                                return new SimpleStringProperty(servicio.formatoCarros());
                            }
                        });


    }

    public ObservableList<Servicio> listarServicios(){
        return FXCollections.observableList(servicioService.listaServicios());
    }
}
