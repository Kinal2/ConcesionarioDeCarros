package com.grupo4.webapp.concesionario.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grupo4.webapp.concesionario.model.Cliente;
import com.grupo4.webapp.concesionario.model.Empleado;
import com.grupo4.webapp.concesionario.service.EmpleadoService;
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
public class EmpleadoFXController implements Initializable{

    @FXML
    TextField tfId, tfNombre, tfApellido, tfTelefono, tfDPI, tfBuscar;
    @FXML
    TextArea taDireccion;
    @FXML
    Button btnGuardar, btnVaciar, btnRegresar, btnEliminar, btnBuscar;
    @FXML
    TableView tblEmpleados;
    @FXML
    TableColumn colId, colApellido, colTelefono, colNombre, colDireccion, colDPI;

    @Setter
    private Main stage;

    private Boolean editar = false;

    @Autowired
    EmpleadoService empleadoService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public void handleButtonAction(ActionEvent event){
        if (event.getSource() == btnGuardar) {
            if (!editar) {
                agregarEmpleado();
            }else{
                editarEmpleado();
            }
        }else if (event.getSource() == btnEliminar) {
            eliminarEmpleado();
        }else if (event.getSource() == btnBuscar) {
            buscarEmpleado();
        }else if (event.getSource() == btnRegresar) {
            stage.indexView();
        }else if (event.getSource() == btnVaciar) {
            vaciarForm();
        }
    }

    
    public void cargarDatos(){
        tblEmpleados.setItems(listarEmpleado());
        colId.setCellValueFactory(new PropertyValueFactory<Empleado, Long>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellido"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Empleado, String>("telefono"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Empleado, String>("direccion"));
        colDPI.setCellValueFactory(new PropertyValueFactory<Empleado, String>("dpi"));
    }

    public void cargarForm(){
        Empleado empleado = (Empleado)tblEmpleados.getSelectionModel().getSelectedItem();
        if (empleado != null) {
            tfId.setText(empleado.getId().toString());
            tfNombre.setText(empleado.getNombre());
            tfApellido.setText(empleado.getApellido());
            tfTelefono.setText(empleado.getTelefono());
            taDireccion.setText(empleado.getDireccion());
            tfDPI.setText(empleado.getDpi());
            editar = true;
        }
    }

    public void vaciarForm(){
        tfId.clear();
        tfNombre.clear();
        tfApellido.clear();
        tfTelefono.clear();
        taDireccion.clear();
        tfDPI.clear();
        editar = false;
    }

    public ObservableList<Empleado> listarEmpleado(){
        return FXCollections.observableList(empleadoService.listarEmpleados());
    }

    public void agregarEmpleado(){
        Empleado empleado = new Empleado();
        empleado.setNombre(tfNombre.getText());
        empleado.setApellido(tfApellido.getText());
        empleado.setTelefono(tfTelefono.getText());
        empleado.setDireccion(taDireccion.getText());
        empleado.setDpi(tfDPI.getText());
        empleadoService.guardarEmpleado(empleado, MethodType.POST);
        cargarDatos();
    }

    public void editarEmpleado(){
        Empleado empleado = empleadoService.buscarEmpleadoPorId(Long.parseLong(tfId.getText()));
        empleado.setNombre(tfNombre.getText());
        empleado.setApellido(tfApellido.getText());
        empleado.setTelefono(tfTelefono.getText());
        empleado.setDireccion(taDireccion.getText());
        empleado.setDpi(tfDPI.getText());
        empleadoService.guardarEmpleado(empleado, MethodType.PUT);
        cargarDatos();
    }

    public void eliminarEmpleado(){
        Empleado empleado = empleadoService.buscarEmpleadoPorId(Long.parseLong(tfId.getText()));
        empleadoService.eliminarEmpleado(empleado);
        cargarDatos();
    }

    public Empleado buscar(){
        return empleadoService.buscarEmpleadoPorId(Long.parseLong(tfBuscar.getText()));
    }

    public void buscarEmpleado() {
        tblEmpleados.getItems().clear();
        if (tfBuscar.getText().isBlank()) {
            cargarDatos();
        } else {
            Empleado empleado = buscar();
            if (empleado != null) {
                tblEmpleados.getItems().add(empleado);
            }
            colId.setCellValueFactory(new PropertyValueFactory<Empleado, Long>("id"));
            colNombre.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombre"));
            colApellido.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellido"));
            colTelefono.setCellValueFactory(new PropertyValueFactory<Empleado, String>("telefono"));
            colDireccion.setCellValueFactory(new PropertyValueFactory<Empleado, String>("direccion"));
            colDPI.setCellValueFactory(new PropertyValueFactory<Empleado, String>("dpi"));
        }
    }
}
