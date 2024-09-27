package com.grupo4.webapp.concesionario.system;

import java.io.IOException;
import java.io.InputStream;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.grupo4.webapp.concesionario.ConcesionarioApplication;
import com.grupo4.webapp.concesionario.controller.FXController.AccesorioFXController;
import com.grupo4.webapp.concesionario.controller.FXController.CarroFXController;
import com.grupo4.webapp.concesionario.controller.FXController.MarcaControllerFX;
import com.grupo4.webapp.concesionario.controller.FXController.VentaControllerFX;
import com.grupo4.webapp.concesionario.service.AccesorioService;
import com.grupo4.webapp.concesionario.controller.FXController.CategoriaCarroFXController;
import com.grupo4.webapp.concesionario.controller.FXController.ClienteFXController;
import com.grupo4.webapp.concesionario.controller.FXController.EmpleadoFXController;
import com.grupo4.webapp.concesionario.controller.FXController.FormLoginController;
import com.grupo4.webapp.concesionario.controller.FXController.IndexController;
import com.grupo4.webapp.concesionario.controller.FXController.LoginController;
import com.grupo4.webapp.concesionario.controller.FXController.ServicioFXController;

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
    public void init() {
        this.applicationContext = new SpringApplicationBuilder(ConcesionarioApplication.class).run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
       this.stage = primaryStage;
       stage.setTitle("Concesionario de Autos");
       loginView();
       stage.show();
    }

    public Initializable switchScene(String fxmlName, int width, int height) throws IOException {
        Initializable resultado = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(applicationContext::getBean);
        InputStream archivo = Main.class.getResourceAsStream("/templates/" + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource("/templates/" + fxmlName));
        scene = new Scene((AnchorPane) loader.load(archivo), width, height);
        stage.setScene(scene);
        stage.sizeToScene();

        resultado = (Initializable) loader.getController();
        return resultado;
    }

    public void indexView() {
        try {
            IndexController indexView = (IndexController) switchScene("index.fxml", 1000, 650);
            indexView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loginView(){
        try {
            LoginController loginView = (LoginController)switchScene("LoginView.fxml", 500, 600);
            loginView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void formLoginView(){
        try {
            FormLoginController userView = (FormLoginController)switchScene("FormUser.fxml", 500, 600);
            userView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clienteView() {
        try {
            ClienteFXController clienteView = (ClienteFXController) switchScene("ClienteView.fxml", 1400, 750);
            clienteView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ventaView(){
        try {
            VentaControllerFX ventaView = (VentaControllerFX) switchScene("VentasView.fxml", 1400, 750);
            ventaView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void marcaView(){
        try {
            MarcaControllerFX marcaView = (MarcaControllerFX) switchScene("MarcasView.fxml", 1000, 650);
            marcaView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void servicioView() {
        try {
            ServicioFXController servicioView = (ServicioFXController) switchScene("ServicioView.fxml", 1400, 750);
            servicioView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void empleadoView() {
        try {
            EmpleadoFXController empleadoView = (EmpleadoFXController) switchScene("EmpleadoView.fxml", 1400, 750);
            empleadoView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void carroView(){
        try {
            CarroFXController carroView = (CarroFXController)switchScene("CarroView.fxml", 1400, 750);
            carroView.setStage(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void categoriaCarroView(){
        try {
            CategoriaCarroFXController catergoriaCarroService = (CategoriaCarroFXController)switchScene("CategoriaCarroView.fxml", 1000, 650);
            catergoriaCarroService.setStage(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void accesoriosView(){
        try {
            AccesorioFXController accesorioView = (AccesorioFXController)switchScene("Accesorio.fxml", 1400, 750);
            accesorioView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
