package com.linkandcelebrate.backend.service;

import com.linkandcelebrate.backend.model.Usuarios;
import com.linkandcelebrate.backend.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UsuariosServiceImplements implements UsuariosService, UserDetailsService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean existeUsername(String username) {
        return usuariosRepository.existsByUsername(username);
    }

    @Override
    public boolean existeEmail(String email) {
        return usuariosRepository.existsByEmail(email);
    }

    @Override
    public void registrarUsuario(String nombreCompleto, String email, String username, String passwordRaw) {
        Usuarios usuario = new Usuarios();
        usuario.setNombreCompleto(nombreCompleto);
        usuario.setEmail(email);
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(passwordRaw));

        usuariosRepository.save(usuario);
    }

    @Override
    public List<Usuarios> getAllUsuarios() {
        return usuariosRepository.findAll();
    }

    @Override
    public Usuarios getUsuarioById(Integer id) {
        return usuariosRepository.findById(id).orElse(null);
    }

    @Override
    public void saveUsuario(Usuarios usuario) {
        usuariosRepository.save(usuario);
    }

    @Override
    public void updateUsuario(Usuarios usuario) {
        usuariosRepository.save(usuario);
    }

    @Override
    public void deleteUsuario(Integer id) {
        usuariosRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarios usuario = usuariosRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                Collections.emptyList()
        );
    }
}