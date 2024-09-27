package com.grupo4.webapp.concesionario.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grupo4.webapp.concesionario.model.Usuario;
import com.grupo4.webapp.concesionario.service.UsuarioService;
import com.grupo4.webapp.concesionario.system.Main;
import com.grupo4.webapp.concesionario.util.ConcesionarioAlert;
import com.grupo4.webapp.concesionario.util.PasswordUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.Setter;
@Component
public class FormLoginController implements Initializable{

    @FXML
    TextField tfUser, tfPassword;

    @FXML
    Button btnCrear, btnRegresar;

    @Setter
    private Main stage;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PasswordUtils passwordUtils;

    @Autowired
    ConcesionarioAlert concesionarioAlert;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCrear){
            agregarUsuario();
            concesionarioAlert.mostrarAlertaInfo(401);
            stage.LoginView();
        }else if(event.getSource() == btnRegresar){
            stage.LoginView();
        }
    }

    public void agregarUsuario(){
        Usuario usuario = new Usuario();
        usuario.setUser(tfUser.getText());
        usuario.setPassword(passwordUtils.encryptedPassword(tfPassword.getText()));
        usuarioService.guardarUsuario(usuario);
    }

}

