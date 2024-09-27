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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.Setter;

@Component
public class LoginController implements Initializable {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PasswordUtils passwordUtils;

    @Autowired
    ConcesionarioAlert concesionarioAlert;

    @Setter
    private Main stage;


    @FXML
    TextField tfUser;

    @FXML
    PasswordField tfPassword;

    @FXML
    Button btnIniciar, btnCrear;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }


    @FXML
    public void handleButtonAction(ActionEvent event) {

        if (event.getSource() == btnIniciar) {
            Usuario user = usuarioService.buscarUsuarioPorNombre(tfUser.getText());
                if (user != null) {
                    if (passwordUtils.checkPassword(tfPassword.getText(), user.getPassword())) {
                        concesionarioAlert.alertaSaludo(user.getUser());
                        stage.indexView();
                    } else {
                    concesionarioAlert.mostrarAlertaInfo(408);
                    }

                } else {
                   concesionarioAlert.mostrarAlertaInfo(407);
                }   
            } else if (event.getSource() == btnCrear) {
                stage.formLoginView();
            }

        
    }

}
