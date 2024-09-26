package com.grupo4.webapp.concesionario.service;


public interface IUsuarioService {
    public List<Usuario> listarUsuarios();

    public Usuario buscarUsuarioPorId(Long id);

    public Usuario guardarUsuario(Usuario usuario ,MethodType methodType);

    public void eliminarUsuario(Usuario usuario);
}
