package com.grupo4.webapp.concesionario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.grupo4.webapp.concesionario.model.Cliente;
import com.grupo4.webapp.concesionario.repository.ClienteRepository;
import com.grupo4.webapp.concesionario.util.ConcesionarioAlert;
import com.grupo4.webapp.concesionario.util.MethodType;

import javafx.scene.control.ButtonType;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ConcesionarioAlert concesionarioAlert;

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarClientePorId(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @Override
    public Cliente guardarCliente(Cliente cliente, MethodType methodType) {
        if (methodType == MethodType.POST) {
            if (clienteRepository.existsById(cliente.getDpi())) {
                concesionarioAlert.mostrarAlertaInfo(406);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El DPI ya pertenece a otro cliente.");

            }
            concesionarioAlert.mostrarAlertaInfo(401);
            return clienteRepository.save(cliente);
        } else {
            try {
                if (concesionarioAlert.mostrarAlertaConfirmacion(106).get() == ButtonType.OK) {
                    concesionarioAlert.mostrarAlertaInfo(401);
                    return clienteRepository.save(cliente);
                }

            } catch (Exception e) {
                concesionarioAlert.mostrarAlertaInfo(404);
            }

        }
        return cliente;

    }

    @Override
    public void eliminarCliente(Cliente cliente) {
        try {
            if (concesionarioAlert.mostrarAlertaConfirmacion(405).get() == ButtonType.OK) {
                clienteRepository.delete(cliente);
                concesionarioAlert.mostrarAlertaInfo(401);
            }

        } catch (Exception e) {
            concesionarioAlert.mostrarAlertaInfo(404);
        }

    }

}
