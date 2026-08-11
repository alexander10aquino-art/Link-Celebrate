package com.linkandcelebrate.backend.service;

import com.linkandcelebrate.backend.model.Usuarios;
import com.linkandcelebrate.backend.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService; // <-- IMPORTANTE
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
    public boolean existeEmail(String email) {
        return usuariosRepository.existsByGmail(email);
    }

    @Override
    public void registrarUsuario(String nombreCompleto, String email, String passwordRaw, String telefono) {
        Usuarios usuario = new Usuarios();
        usuario.setNombre(nombreCompleto);
        usuario.setGmail(email);
        usuario.setContrasena(passwordEncoder.encode(passwordRaw));
        usuario.setTelefono(telefono != null ? telefono : "+50200000000");

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
        if (usuario.getContrasena() != null && !usuario.getContrasena().startsWith("$2a$")) {
            usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        }
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

    // Spring Security valida contra el campo gmail
    @Override
    public UserDetails loadUserByUsername(String gmail) throws UsernameNotFoundException {
        Usuarios usuario = usuariosRepository.findByGmail(gmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el correo: " + gmail));

        return new User(
                usuario.getGmail(),
                usuario.getContrasena(),
                Collections.emptyList()
        );
    }
}