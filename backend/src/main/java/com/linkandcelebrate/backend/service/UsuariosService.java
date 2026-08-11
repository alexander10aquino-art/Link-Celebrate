package com.linkandcelebrate.backend.service;

import com.linkandcelebrate.backend.model.Usuarios;
import java.util.List;

public interface UsuariosService {
    // Registro y Login
    boolean existeUsername(String username);
    boolean existeEmail(String email);
    void registrarUsuario(String nombreCompleto, String email, String username, String passwordRaw);

    // Métodos para UsuariosController
    List<Usuarios> getAllUsuarios();
    Usuarios getUsuarioById(Integer id);
    void saveUsuario(Usuarios usuario);
    void updateUsuario(Usuarios usuario);
    void deleteUsuario(Integer id);
}