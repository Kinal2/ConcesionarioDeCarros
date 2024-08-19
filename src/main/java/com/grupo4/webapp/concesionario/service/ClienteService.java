package com.grupo4.webapp.concesionario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.grupo4.webapp.concesionario.model.Cliente;
import com.grupo4.webapp.concesionario.repository.ClienteRepository;
import com.grupo4.webapp.concesionario.util.MethodType;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    ClienteRepository clienteRepository;

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
        if(methodType == MethodType.POST){
            if (clienteRepository.existsById(cliente.getDpi())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El DPI ya pertenece a otro cliente.");
            }
            return clienteRepository.save(cliente);
        }else{
            return clienteRepository.save(cliente);
        }
        
        
    }

    @Override
    public void eliminarCliente(Cliente cliente) {
        clienteRepository.delete(cliente);
    }

}
