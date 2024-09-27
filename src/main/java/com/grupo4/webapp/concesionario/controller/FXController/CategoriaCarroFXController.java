package com.grupo4.webapp.concesionario.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grupo4.webapp.concesionario.model.CategoriaCarro;
import com.grupo4.webapp.concesionario.service.CategoriaCarroService;
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
public class CategoriaCarroFXController implements Initializable{

    @FXML
    TextField tfId, tfNombre, tfBuscar;
    @FXML
    TextArea taDescripcion;
    @FXML
    Button btnGuardar, btnVaciar, btnBuscar, btnEliminar, btnRegresar;
    @FXML
    TableView tblCategoria;
    @FXML
    TableColumn colId, colNom, colDes;

    @Setter
    private Main stage;

    private Boolean editar = false;

    @Autowired
    CategoriaCarroService categoriaCarroService;
    
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();

    }

    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(!editar){
                agregarCategoriaCarro();
            }else{
                editarCategoriaCarro();
            }
        }else if(event.getSource() == btnVaciar){
            limpiarFomrEditar();
        }else if(event.getSource() == btnEliminar){
            eliminarCategoriaCarro();
        }else if(event.getSource() == btnBuscar) {
            tblCategoria.getItems().clear();
            if (tfBuscar.getText().isBlank()) {
                cargarDatos();
            } else {
                tblCategoria.getItems().add(buscarCategoriaCarro());
                colId.setCellValueFactory(new PropertyValueFactory<CategoriaCarro, Long>("id"));
                colNom.setCellValueFactory(new PropertyValueFactory<CategoriaCarro, String>("nombreCategoriaCarro"));
                colDes.setCellValueFactory(new PropertyValueFactory<CategoriaCarro, String>("descripcionCategoriaCarro"));

            }
        }else if(event.getSource() == btnRegresar){
            stage.indexView();
        }
    }

    public void cargarDatos(){
        tblCategoria.setItems(listarCategoriaCarro());
        colId.setCellValueFactory(new PropertyValueFactory<CategoriaCarro, Long>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<CategoriaCarro, String>("nombreCategoriaCarro"));
        colDes.setCellValueFactory(new PropertyValueFactory<CategoriaCarro, String>("descripcionCategoriaCarro"));
    }

    public void cargarFormEditar(){
        CategoriaCarro categoriaCarro = (CategoriaCarro)tblCategoria.getSelectionModel().getSelectedItem();
        if(categoriaCarro != null){
            tfId.setText(Long.toString(categoriaCarro.getId()));
            tfNombre.setText(categoriaCarro.getNombreCategoriaCarro());
            taDescripcion.setText(categoriaCarro.getDescripcionCategoriaCarro());
            editar = true;
        }
    }

    public void limpiarFomrEditar(){
        tfBuscar.clear();
        tfId.clear();
        tfNombre.clear();
        taDescripcion.clear();
        editar = false;
    }

    public ObservableList<CategoriaCarro> listarCategoriaCarro(){
        return FXCollections.observableList(categoriaCarroService.listarCategoriaCarro());
    }

    public void agregarCategoriaCarro(){
        CategoriaCarro categoriaCarro = new CategoriaCarro();
        categoriaCarro.setNombreCategoriaCarro(tfNombre.getText());
        categoriaCarro.setDescripcionCategoriaCarro(taDescripcion.getText());
        categoriaCarroService.guardarCategoriaCarros(categoriaCarro, MethodType.POST);
        cargarDatos();
    }

    public void editarCategoriaCarro(){
        CategoriaCarro categoriaCarro = categoriaCarroService.buscarCategoriaCarro(Long.parseLong(tfId.getText()));
        categoriaCarro.setNombreCategoriaCarro(tfNombre.getText());
        categoriaCarro.setDescripcionCategoriaCarro(taDescripcion.getText());
        categoriaCarroService.guardarCategoriaCarros(categoriaCarro, MethodType.PUT);
        cargarDatos();
    }

    public void eliminarCategoriaCarro(){
        CategoriaCarro categoriaCarro = categoriaCarroService.buscarCategoriaCarro(Long.parseLong(tfId.getText()));
        categoriaCarroService.eliminarCategoriaCarros(categoriaCarro);
        cargarDatos();
    }

    public CategoriaCarro buscarCategoriaCarro(){
        return categoriaCarroService.buscarCategoriaCarro(Long.parseLong(tfBuscar.getText()));
    }
    
}
