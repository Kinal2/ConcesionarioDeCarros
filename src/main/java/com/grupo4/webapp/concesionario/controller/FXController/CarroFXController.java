package com.grupo4.webapp.concesionario.controller.FXController;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grupo4.webapp.concesionario.model.Accesorio;
import com.grupo4.webapp.concesionario.model.Carro;
import com.grupo4.webapp.concesionario.model.CategoriaCarro;
import com.grupo4.webapp.concesionario.model.Marca;
import com.grupo4.webapp.concesionario.model.Servicio;
import com.grupo4.webapp.concesionario.service.AccesorioService;
import com.grupo4.webapp.concesionario.service.CarroService;
import com.grupo4.webapp.concesionario.service.CategoriaCarroService;
import com.grupo4.webapp.concesionario.service.MarcaService;
import com.grupo4.webapp.concesionario.system.Main;
import com.grupo4.webapp.concesionario.util.EstadoCarro;
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
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import lombok.Setter;

@Component
public class CarroFXController implements Initializable{

    @FXML
    TextField tfId, tfModelo, tfAño, tfPrecio, tfColor, tfKilometraje, tfBuscar;

    @FXML
    Button btnGuardar, btnVaciar, btnEliminar, btnRegresar, btnBuscar;

    @FXML
    ComboBox cmbEstado, cmbMarca, cmbCategoria;

    @FXML
    ListView lvAccesorios;

    @FXML
    TableView tblCarros;

    @FXML
    TableColumn colId, colModelo, colAño, colPrecio, colColor, colKilometraje, colEstado, colMarca, colCategoria, colAccesorios;

    @Autowired
    CarroService carroService;

    @Autowired
    CategoriaCarroService categoriaCarroService;

    @Autowired
    MarcaService marcaService;

    @Autowired
    AccesorioService accesorioService;


    @Setter
    private Main stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbMarca.setItems(listarMarcas());
        cmbCategoria.setItems(listarCategorias());
        lvAccesorios.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvAccesorios.setItems(listarAccesorios());
    }

    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(tfId.getText().isBlank()){
                agregarCarro();
            } else {
                editarCarro();
            }
        }else if(event.getSource() == btnVaciar){
            LimpiarFormEditar();
        }else if(event.getSource() == btnRegresar){
            stage.indexView();
        }else if(event.getSource() == btnEliminar){
            eliminarCarro();
        }else if(event.getSource() == btnBuscar){
            buscarCarro();
        }
    }

    public void cargarDatos(){
        tblCarros.setItems(listarCarros());
        colId.setCellValueFactory(new PropertyValueFactory<Carro, Long>("id"));
        colModelo.setCellValueFactory(new PropertyValueFactory<Carro, String>("modelo"));
        colAño.setCellValueFactory(new PropertyValueFactory<Carro, Integer>("año"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<Carro, Double>("precio"));
        colColor.setCellValueFactory(new PropertyValueFactory<Carro, String>("color"));
        colKilometraje.setCellValueFactory(new PropertyValueFactory<Carro, Integer>("kilometraje"));
        colEstado.setCellValueFactory(new PropertyValueFactory<Carro, EstadoCarro>("estado"));
        colMarca.setCellValueFactory(new PropertyValueFactory<Carro, Marca>("marca"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<Carro, CategoriaCarro>("categoria"));
        colAccesorios.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Carro, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TableColumn.CellDataFeatures<Carro, String> cellData) {
                        Carro carro = cellData.getValue();
                        return new SimpleStringProperty(carro.formatoAccesorios());
                    }
                });
    }

    public ObservableList<Carro> listarCarros(){
        return FXCollections.observableList(carroService.listarCarro());
    }
      
    public ObservableList<Marca> listarMarcas(){
        return FXCollections.observableList(marcaService.listarMarcas());
    }

    public ObservableList<CategoriaCarro> listarCategorias(){
        return FXCollections.observableList(categoriaCarroService.listarCategoriaCarro());
    }

    public ObservableList<Accesorio> listarAccesorios(){
        return FXCollections.observableList(accesorioService.listarAccesorios());
    }

    public void cargarFormEditar(){
        Carro carro = (Carro)tblCarros.getSelectionModel().getSelectedItem();
        if(carro != null){
            tfId.setText(Long.toString(carro.getId()));
            tfModelo.setText(carro.getModelo());
            tfAño.setText(Integer.toString(carro.getAño()));
            tfPrecio.setText(Double.toString(carro.getPrecio()));
            tfColor.setText(carro.getColor());
            tfKilometraje.setText(Integer.toString(carro.getKilometraje()));
            cmbEstado.getSelectionModel().select(carro.getEstado());
            cmbMarca.getSelectionModel().select(carro.getMarca());
            cmbCategoria.getSelectionModel().select(carro.getCategoria());
            lvAccesorios.getSelectionModel().clearSelection();
            for (Accesorio accesorio : carro.getAccesorios()) {
                lvAccesorios.getSelectionModel().select(obtenerIndexAccesorios(accesorio));
            }

        }
    }

    public void LimpiarFormEditar(){
        tfId.clear();
        tfModelo.clear();
        tfAño.clear();
        tfPrecio.clear();
        tfColor.clear();
        tfKilometraje.clear();
        cmbEstado.getSelectionModel().clearSelection();
        cmbMarca.getSelectionModel().clearSelection();
        cmbCategoria.getSelectionModel().clearSelection();
        lvAccesorios.getSelectionModel().clearSelection();
    }

    public void agregarCarro(){
        Carro carro = new Carro();
        carro.setModelo(tfModelo.getText());
        carro.setAño(Integer.parseInt(tfAño.getText()));
        carro.setPrecio(Double.parseDouble(tfPrecio.getText()));
        carro.setColor(tfColor.getText());
        carro.setKilometraje(Integer.parseInt(tfKilometraje.getText()));
        carro.setMarca((Marca)cmbMarca.getSelectionModel().getSelectedItem());
        carro.setCategoria((CategoriaCarro)cmbCategoria.getSelectionModel().getSelectedItem());
        carro.setAccesorios(lvAccesorios.getSelectionModel().getSelectedItems());
        carroService.guardarCarro(carro, MethodType.POST);
        cargarDatos();
    }

    public void editarCarro(){
        Carro carro = carroService.buscarCarroPorId(Long.parseLong(tfId.getText()));
        carro.setModelo(tfModelo.getText());
        carro.setAño(Integer.parseInt(tfAño.getText()));
        carro.setPrecio(Double.parseDouble(tfPrecio.getText()));
        carro.setColor(tfColor.getText());
        carro.setKilometraje(Integer.parseInt(tfKilometraje.getText()));
        carro.setMarca((Marca)cmbMarca.getSelectionModel().getSelectedItem());
        carro.setCategoria((CategoriaCarro)cmbCategoria.getSelectionModel().getSelectedItem());
        carro.setAccesorios(lvAccesorios.getSelectionModel().getSelectedItems());
        carroService.guardarCarro(carro, MethodType.PUT);
        cargarDatos();
    }

    public void eliminarCarro(){
        Carro carro = carroService.buscarCarroPorId(Long.parseLong(tfId.getText()));
        carroService.eliminarCarro(carro);
        cargarDatos();
    }

    public void buscarCarro(){
        try {
            if(tfBuscar.getText().isEmpty()){
                cargarDatos();
                return;
            }
            Long id = Long.parseLong(tfBuscar.getText());
            Carro carro = carroService.buscarCarroPorId(id);
            if(carro != null){
                ObservableList<Carro> carros = FXCollections.observableArrayList(carro);
                tblCarros.setItems(carros);
            } else {
                LimpiarFormEditar();
                tblCarros.setItems(FXCollections.observableArrayList());
            }
        } catch (NumberFormatException e) {
            LimpiarFormEditar();
            tblCarros.setItems(FXCollections.observableArrayList());
            
        } catch (Exception e){
            LimpiarFormEditar();
            tblCarros.setItems(FXCollections.observableArrayList());
        }
    }

    public int obtenerIndexAccesorios(Accesorio accesorio) {
        int index = 0;
        for (int i = 0; i <= lvAccesorios.getItems().size(); i++) {
            String accesorioLv = lvAccesorios.getItems().get(i).toString();
            String accesorioTbl = accesorio.toString();

            if (accesorioLv.equals(accesorioTbl)) {
                index = i;
                break;
            }
        }
        return index;
    }

}
