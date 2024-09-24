package com.grupo4.webapp.concesionario.system;

import java.io.IOException;
import java.io.InputStream;
 
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.grupo4.webapp.concesionario.ConcesionarioApplication;
import com.grupo4.webapp.concesionario.controller.FXController.ClienteFXController;
import com.grupo4.webapp.concesionario.controller.FXController.IndexController;
import com.grupo4.webapp.concesionario.controller.FXController.ServicioFXController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
       stage.setTitle("Biblioteca Kinal Spring");
       indexView();
       stage.show();
    }
 
    public Initializable switchScene(String fxmlName, int width, int height) throws IOException{
        Initializable resultado = null;
        FXMLLoader loader = new FXMLLoader();
 
        loader.setControllerFactory(applicationContext:: getBean);
        InputStream archivo  =  Main.class.getResourceAsStream("/templates/" + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource("/templates/" + fxmlName));
        Image icon = new Image("/images/Logo.png");
        scene = new Scene((AnchorPane) loader.load(archivo),width,height);
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.sizeToScene();
 
        resultado = (Initializable)loader.getController();
        return resultado;
    }
 
    public void indexView(){
        try {
            IndexController indexView = (IndexController)switchScene("index.fxml", 1000, 650);
            indexView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ClienteView(){
        try {
            ClienteFXController clienteView = (ClienteFXController)switchScene("ClienteView.fxml", 1400, 750);
            clienteView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ServicioView(){
        try {
            ServicioFXController servicioView = (ServicioFXController)switchScene("ServicioView.fxml", 1400, 750);
            servicioView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}