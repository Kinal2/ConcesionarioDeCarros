package com.grupo4.webapp.concesionario.system;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.grupo4.webapp.concesionario.ConcesionarioApplication;
import com.grupo4.webapp.concesionario.controller.FXController.MarcaControllerFX;
import com.grupo4.webapp.concesionario.controller.FXController.VentaControllerFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    private ConfigurableApplicationContext applicationContext;
    private Stage stage;
    private Scene scene;

    @Override
    public void init(){
        this.applicationContext = new SpringApplicationBuilder(ConcesionarioApplication.class).run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        stage.setTitle("Concecionario Kinal");
        stage.show();
    }

    public Initializable switchScene(String fxmlName, int width, int height) throws IOException{
        Initializable resultado = null;
        FXMLLoader loader = new FXMLLoader();

        loader.setControllerFactory(applicationContext:: getBean);
        InputStream archivo  =  Main.class.getResourceAsStream("/templates/" + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource("/templates/" + fxmlName));

        scene = new Scene((AnchorPane) loader.load(archivo),width,height);
        stage.setScene(scene);
        stage.sizeToScene();

        resultado = (Initializable)loader.getController();
        return resultado;
    }

    public void MarcasView(){
        try {
            MarcaControllerFX marcaView = (MarcaControllerFX)switchScene("MarcasView.fxml",1000,650);
            marcaView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void VentaView(){
        try {
            VentaControllerFX ventaView = (VentaControllerFX)switchScene("VentasView.fxml",1400, 750);
            ventaView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
