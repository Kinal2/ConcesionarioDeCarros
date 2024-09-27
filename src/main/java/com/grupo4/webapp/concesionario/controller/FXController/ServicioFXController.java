package com.grupo4.webapp.concesionario.controller.FXController;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grupo4.webapp.concesionario.model.Carro;
import com.grupo4.webapp.concesionario.model.Servicio;
import com.grupo4.webapp.concesionario.service.CarroService;
import com.grupo4.webapp.concesionario.service.ServicioService;
import com.grupo4.webapp.concesionario.system.Main;
import com.grupo4.webapp.concesionario.util.MethodType;

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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import lombok.Setter;

@Component
public class ServicioFXController implements Initializable {

    @Autowired
    ServicioService servicioService;

    @Autowired
    CarroService carroService;

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
    TableColumn colId, colEntrada, colSalida, colCompletado, colDescripcion, colCarros, colCosto;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lvCarros.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        cargarDatos();
        
        cmbCompletado.getItems().add("True");
        cmbCompletado.getItems().add("False");
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnGuardar) {
            if(tfId.getText().isEmpty()){
                agregarServicio();
            }else{
                editarServicio();
            }
        } else if (event.getSource() == btnVaciar) {
            vaciarForm();
        } else if (event.getSource() == btnRegresar) {
            stage.indexView();
        } else if (event.getSource() == btnEliminar) {
            eliminarServicio();
        } else if (event.getSource() == btnBuscar) {
            tblServicios.getItems().clear();
            if (tfBuscar.getText().isBlank()) {
                cargarDatos();
            } else {
                tblServicios.getItems().add(buscarServicio());
                colId.setCellValueFactory(new PropertyValueFactory<Servicio, Long>("id"));
                colEntrada.setCellValueFactory(new PropertyValueFactory<Servicio, Date>("fechadeEntrada"));
                colSalida.setCellValueFactory(new PropertyValueFactory<Servicio, Date>("fechadeSalida"));
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

        }
    }

    public void cargarDatos() {
        lvCarros.setItems(llenarCarros());
        tblServicios.setItems(listarServicios());
        colId.setCellValueFactory(new PropertyValueFactory<Servicio, Long>("id"));
        colEntrada.setCellValueFactory(new PropertyValueFactory<Servicio, Date>("fechadeEntrada"));
        colSalida.setCellValueFactory(new PropertyValueFactory<Servicio, Date>("fechadeSalida"));
        colCompletado.setCellValueFactory(new PropertyValueFactory<Servicio, Boolean>("completado"));
        colCosto.setCellValueFactory(new PropertyValueFactory<Servicio, Double>("costo"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Servicio, String>("descripcion"));
        colCarros.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Servicio, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Servicio, String> cellData) {
                        Servicio servicio = cellData.getValue();
                        return new SimpleStringProperty(servicio.formatoCarros());
                    }
                });

    }

    public ObservableList<Carro> llenarCarros() {
        return FXCollections.observableList(carroService.listarCarro());
    }

    public void cargarForm() {
        Servicio servicio = (Servicio) tblServicios.getSelectionModel().getSelectedItem();
        int completado = 1;
        if (servicio != null) {
            tfId.setText(servicio.getId().toString());
            tfCosto.setText(servicio.getCosto().toString());
            taDescripcion.setText(servicio.getDescripcion());
            dpEntrada.setValue(servicio.getFechadeEntrada().toLocalDate());
            dpSalida.setValue(servicio.getFechadeSalida().toLocalDate());
            if (servicio.getCompletado()) {
                completado = 0;
            }
            cmbCompletado.getSelectionModel().select(completado);
            lvCarros.getSelectionModel().clearSelection();
            for (Carro carro : servicio.getCarros()) {
                lvCarros.getSelectionModel().select(obtenerIndexCarros(carro));
            }
        }
    }

    public void vaciarForm() {
        tfId.clear();
        tfCosto.clear();
        taDescripcion.clear();
        dpEntrada.setValue(null);
        dpSalida.setValue(null);
        cmbCompletado.getSelectionModel().clearSelection();
        lvCarros.getSelectionModel().clearSelection();
    }

    public void agregarServicio() {
        Servicio servicio = new Servicio();
        servicio.setCosto(Double.parseDouble(tfCosto.getText()));
        servicio.setDescripcion(taDescripcion.getText());
        servicio.setCarros(lvCarros.getSelectionModel().getSelectedItems());
        servicioService.guardarServicio(servicio, MethodType.POST);
        cargarDatos();
    }

    public void editarServicio() {
        Servicio servicio = servicioService.buscarServicioPorId(Long.parseLong(tfId.getText()));
        servicio.setCosto(Double.parseDouble(tfCosto.getText()));
        servicio.setDescripcion(taDescripcion.getText());
        servicio.setCompletado(Boolean.valueOf(cmbCompletado.getSelectionModel().getSelectedItem().toString()));
        servicio.setFechadeEntrada(Date.valueOf(dpEntrada.getValue()));
        servicio.setFechadeSalida(Date.valueOf(dpSalida.getValue()));
        servicio.setCarros(lvCarros.getSelectionModel().getSelectedItems());
        servicioService.guardarServicio(servicio, MethodType.PUT);
        cargarDatos();
    }

    public void eliminarServicio() {
        Servicio servicio = servicioService.buscarServicioPorId(Long.parseLong(tfId.getText()));
        servicioService.eliminarServicio(servicio);
        cargarDatos();
    }

    public Servicio buscarServicio() {
        return servicioService.buscarServicioPorId(Long.parseLong(tfBuscar.getText()));
    }

    public int obtenerIndexCarros(Carro carro) {
        int index = 0;
        for (int i = 0; i <= lvCarros.getItems().size(); i++) {
            String carroLv = lvCarros.getItems().get(i).toString();
            String carroTbl = carro.toString();

            if (carroLv.equals(carroTbl)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public ObservableList<Servicio> listarServicios() {
        return FXCollections.observableList(servicioService.listaServicios());
    }

}
