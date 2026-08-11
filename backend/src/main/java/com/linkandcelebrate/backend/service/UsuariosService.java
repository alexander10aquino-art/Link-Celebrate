package com.linkandcelebrate.backend.service;

import com.linkandcelebrate.backend.model.Usuarios;
import java.util.List;

public interface UsuariosService {
    // Métodos para Registro y Login
    boolean existeEmail(String email);
    void registrarUsuario(String nombreCompleto, String email, String passwordRaw, String telefono);

    // Métodos para el CRUD
    List<Usuarios> getAllUsuarios();
    Usuarios getUsuarioById(Integer id);
    void saveUsuario(Usuarios usuario);
    void updateUsuario(Usuarios usuario);
    void deleteUsuario(Integer id);
}