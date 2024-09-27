package com.grupo4.webapp.concesionario.controller.FXController;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grupo4.webapp.concesionario.model.Carro;
import com.grupo4.webapp.concesionario.model.Cliente;
import com.grupo4.webapp.concesionario.model.Empleado;
import com.grupo4.webapp.concesionario.model.Marca;
import com.grupo4.webapp.concesionario.model.Venta;
import com.grupo4.webapp.concesionario.service.CarroService;
import com.grupo4.webapp.concesionario.service.ClienteService;
import com.grupo4.webapp.concesionario.service.EmpleadoService;
import com.grupo4.webapp.concesionario.service.VentaService;
import com.grupo4.webapp.concesionario.system.Main;
import com.grupo4.webapp.concesionario.util.MethodType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Setter;

@Component
public class VentaControllerFX implements Initializable {

    @FXML
    TextField tfId, tfFecha, tfPrecio, tfBuscar;
    @FXML
    Button btnGuardar,btnRegresar,btnBuscar,btnEliminar, btnLimpiar;
    @FXML
    TableView tblVentas;
    @FXML
    TableColumn colId,colFecha,colPrecio,colCarros,colClientes,colEmpleados;
    @FXML
    ComboBox cmbCarros, cmbClientes, cmbEmpleados;

    @Setter
    private Main stage;

    @Autowired
    VentaService ventaService;
    @Autowired
    ClienteService clienteService;
    @Autowired
    CarroService carroService;
    @Autowired
    EmpleadoService empleadoService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        llenarCMB();
    }

    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(tfId.getText().isBlank()){
                agregarVenta();
            }else{
                editarVenta();
            }
        }else if(event.getSource() == btnRegresar){
            stage.indexView();
        }else if(event.getSource() == btnEliminar){
            eliminarVenta();
        }else if(event.getSource() == btnLimpiar){
            limpiarForm();
        }else if(event.getSource() == btnBuscar){
            tblVentas.getItems().clear();
            if(tfBuscar.getText().isBlank()){
                cargarDatos();
            }else{
                tblVentas.getItems().add(buscarVenta());
                colId.setCellValueFactory(new PropertyValueFactory<Venta,Long>("id"));
                colFecha.setCellValueFactory(new PropertyValueFactory<Venta,Date>("fecha"));
                colPrecio.setCellValueFactory(new PropertyValueFactory<Venta,Double>("precioFinal"));
                colCarros.setCellValueFactory(new PropertyValueFactory<Venta,Carro>("carro"));
                colClientes.setCellValueFactory(new PropertyValueFactory<Venta,Cliente>("cliente"));
                colEmpleados.setCellValueFactory(new PropertyValueFactory<Venta,Empleado>("empleado"));
            }
        }
    }

    public ObservableList<Venta> listarVentas(){
        return FXCollections.observableList(ventaService.listarVentas());
    }

    public void cargarDatos(){
        tblVentas.setItems(listarVentas());
        colId.setCellValueFactory(new PropertyValueFactory<Venta,Long>("id"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Venta,Date>("fecha"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<Venta,Double>("precioFinal"));
        colCarros.setCellValueFactory(new PropertyValueFactory<Venta,Carro>("carro"));
        colClientes.setCellValueFactory(new PropertyValueFactory<Venta,Cliente>("cliente"));
        colEmpleados.setCellValueFactory(new PropertyValueFactory<Venta,Empleado>("empleado"));
    }

    public void llenarCMB(){
        ObservableList<Carro> carros = FXCollections.observableList(carroService.listarCarro());
        ObservableList<Cliente> clientes = FXCollections.observableList(clienteService.listarClientes());
        ObservableList<Empleado> empleados = FXCollections.observableList(empleadoService.listarEmpleados());
        cmbCarros.setItems(carros);
        cmbClientes.setItems(clientes);
        cmbEmpleados.setItems(empleados);
    }

    public void cargarFormEditar(){
        Venta venta = (Venta)tblVentas.getSelectionModel().getSelectedItem();
        tfId.setText(Long.toString(venta.getId()));
        tfFecha.setText(venta.getFecha().toString());
        tfPrecio.setText(Double.toString(venta.getPrecioFinal()));
        cmbCarros.getSelectionModel().select(venta.getCarro());
        cmbClientes.getSelectionModel().select(venta.getCliente());
        cmbEmpleados.getSelectionModel().select(venta.getEmpleado());

    }

    public void limpiarForm(){
        tfId.clear();
        tfFecha.clear();
        tfPrecio.clear();
        cmbCarros.getSelectionModel().clearSelection();
        cmbClientes.getSelectionModel().clearSelection();
        cmbEmpleados.getSelectionModel().clearSelection();
    }

    public void agregarVenta(){
        Venta venta = new Venta();
        Carro carro = (Carro)cmbCarros.getSelectionModel().getSelectedItem();
        Cliente cliente = (Cliente) cmbClientes.getSelectionModel().getSelectedItem();
        Empleado empleado = (Empleado)cmbEmpleados.getSelectionModel().getSelectedItem();
        venta.setFecha(Date.valueOf(LocalDate.now()));
        venta.setPrecioFinal(Double.parseDouble(tfPrecio.getText()));
        venta.setCarro(carro);
        venta.setCliente(cliente);
        venta.setEmpleado(empleado);
        ventaService.guardarVenta(venta, MethodType.POST);
        cargarDatos();
    }

    public void editarVenta(){
        Venta venta = ventaService.buscarVentaPorId(Long.parseLong(tfId.getText()));
        Carro carro =(Carro) cmbCarros.getSelectionModel().getSelectedItem();
        Cliente cliente = (Cliente) cmbClientes.getSelectionModel().getSelectedItem();
        Empleado empleado = (Empleado)cmbEmpleados.getSelectionModel().getSelectedItem();
        venta.setFecha(Date.valueOf(LocalDate.now()));
        venta.setPrecioFinal(Double.parseDouble(tfPrecio.getText()));
        venta.setCarro(carro);
        venta.setCliente(cliente);
        venta.setEmpleado(empleado);
        ventaService.guardarVenta(venta, MethodType.PUT);
        cargarDatos();
    }

    public void eliminarVenta(){
        Venta venta = ventaService.buscarVentaPorId(Long.parseLong(tfId.getText()));
        ventaService.eliminarVenta(venta);
        cargarDatos();
    }

    public Venta buscarVenta(){
        return ventaService.buscarVentaPorId(Long.parseLong(tfBuscar.getText()));
    }

}
