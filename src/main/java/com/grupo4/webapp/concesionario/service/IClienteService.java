package com.grupo4.webapp.concesionario.service;

import java.util.List;

import com.grupo4.webapp.concesionario.model.Cliente;
import com.grupo4.webapp.concesionario.util.MethodType;

public interface IClienteService {

    public List<Cliente>listarClientes();

    public Cliente buscarClientePorId(Long id);

    public Cliente guardarCliente(Cliente cliente, MethodType methodType);
    
    public void eliminarCliente(Cliente cliente);
    

}

