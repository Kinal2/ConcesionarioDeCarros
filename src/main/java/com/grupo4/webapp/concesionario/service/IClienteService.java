package com.grupo4.webapp.concesionario.service;

import java.util.List;

import com.grupo4.webapp.concesionario.model.Cliente;

public interface IClienteService {

    public List<Cliente>listarClientes();

    public Cliente buscarClientePorId(Long id);

    public Cliente guardarCliente(Cliente cliente);
    
    public void eliminarCliente(Cliente cliente);
    
}
