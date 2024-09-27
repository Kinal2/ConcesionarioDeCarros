package com.grupo4.webapp.concesionario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.grupo4.webapp.concesionario.system.Main;

import javafx.application.Application;

@SpringBootApplication
public class ConcesionarioApplication {

	public static void main(String[] args) {
		Application.launch(Main.class, args);
		SpringApplication.run(ConcesionarioApplication.class, args);
	}

}
