package com.grupo4.webapp.concesionario.service;

import java.util.List;
import java.util.Optional;

import com.grupo4.webapp.concesionario.model.Usuario;

public interface IUsuarioService {
    public List<Usuario> listarUsuarios();

    public Usuario buscarUsuarioPorNombre(String user);

    public Usuario guardarUsuario(Usuario usuario);

    public void eliminarUsuario(Usuario usuario);
}
