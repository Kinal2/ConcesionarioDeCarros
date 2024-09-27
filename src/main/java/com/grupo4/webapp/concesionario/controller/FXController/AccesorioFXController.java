package com.grupo4.webapp.concesionario.controller.FXController;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grupo4.webapp.concesionario.model.Accesorio;
import com.grupo4.webapp.concesionario.model.Servicio;
import com.grupo4.webapp.concesionario.service.AccesorioService;
import com.grupo4.webapp.concesionario.system.Main;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;
import lombok.Setter;

@Component
public class AccesorioFXController implements Initializable {

    @FXML
    TextField tfId, tfNombre, tfDescripcion, tfPrecio, tfStock, tfBuscar;
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
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
    }

    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnGuardar) {
            if (tfId.getText().isBlank()) {
                agregarAccesorio();
            } else {
                editarAccesorio();
            }
        } else if (event.getSource() == btnVaciar) {
            vaciarForm();
        } else if (event.getSource() == btnEliminar) {
            eliminarAccesorio();
        } else if (event.getSource() == btnRegresar) {
            stage.indexView();
        } else if (event.getSource() == btnBuscar) {
            tblAccesorios.getItems().clear();
            if (tfBuscar.getText().isBlank()) {
                cargarDatos();
            } else {
                tblAccesorios.getItems().add(buscarAccesorio());
                colId.setCellValueFactory(new PropertyValueFactory<Accesorio, Long>("id"));
                colNombre.setCellValueFactory(new PropertyValueFactory<Accesorio, String>("nombreAccesorio"));
                colDescripcion.setCellValueFactory(new PropertyValueFactory<Accesorio, String>("descripcionAccesorio"));
                colPrecio.setCellValueFactory(new PropertyValueFactory<Accesorio, Double>("precioAccesorio"));
                colStock.setCellValueFactory(new PropertyValueFactory<Accesorio, Integer>("stock"));
            }
        }
    }

    public void cargarDatos() {
        tblAccesorios.setItems(listaAccesorios());
        colId.setCellValueFactory(new PropertyValueFactory<Accesorio, Long>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Accesorio, String>("nombreAccesorio"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Accesorio, String>("descripcionAccesorio"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<Accesorio, Double>("precioAccesorio"));
        colStock.setCellValueFactory(new PropertyValueFactory<Accesorio, Integer>("stock"));
    }

    public void cargarForm() {
        Accesorio accesorio = (Accesorio) tblAccesorios.getSelectionModel().getSelectedItem();
        if (accesorio != null) {
            tfId.setText(Long.toString(accesorio.getId()));
            tfDescripcion.setText(accesorio.getDescripcionAccesorio());
            tfNombre.setText(accesorio.getNombreAccesorio());
            tfPrecio.setText(accesorio.getPrecioAccesorio().toString());
            tfStock.setText(accesorio.getStock().toString());
        }
    }

    public void vaciarForm() {
        tfId.clear();
        tfNombre.clear();
        tfDescripcion.clear();
        tfPrecio.clear();
        tfStock.clear();
    }

    public ObservableList<Accesorio> listaAccesorios() {
        return FXCollections.observableList(accesorioService.listarAccesorios());
    }

    public void agregarAccesorio() {
        Accesorio accesorio = new Accesorio();
        accesorio.setNombreAccesorio(tfNombre.getText());
        accesorio.setDescripcionAccesorio(tfDescripcion.getText());
        accesorio.setPrecioAccesorio(Double.parseDouble(tfPrecio.getText()));
        accesorio.setStock(Integer.parseInt(tfStock.getText()));
        accesorioService.guardarAccesorio(accesorio);
        cargarDatos();
    }

    public void editarAccesorio() {
        Accesorio accesorio = accesorioService.buscaAccesorioPorId(Long.parseLong(tfId.getText()));
        accesorio.setNombreAccesorio(tfNombre.getText());
        accesorio.setDescripcionAccesorio(tfDescripcion.getText());
        accesorio.setPrecioAccesorio(Double.parseDouble(tfPrecio.getText()));
        accesorio.setStock(Integer.parseInt(tfStock.getText()));
        accesorioService.guardarAccesorio(accesorio);
        cargarDatos();
    }

    public void eliminarAccesorio() {
        Accesorio accesorio = accesorioService.buscaAccesorioPorId(Long.parseLong(tfId.getText()));
        accesorioService.eliminarAccesorio(accesorio);
        cargarDatos();
    }

    public Accesorio buscarAccesorio() {
        return accesorioService.buscaAccesorioPorId(Long.parseLong(tfBuscar.getText()));
    }
}
