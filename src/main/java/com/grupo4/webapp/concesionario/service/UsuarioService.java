package com.grupo4.webapp.concesionario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo4.webapp.concesionario.model.Usuario;
import com.grupo4.webapp.concesionario.repository.UsuarioRepository;
@Service
public class UsuarioService implements IUsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario buscarUsuarioPorNombre(String user) {
        return usuarioRepository.findByUser(user);
    }

    

    @Override
    public void eliminarUsuario(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        try {
            return usuarioRepository.save(usuario);
        } catch (Exception e) {
           return usuario;
        }
    }
}