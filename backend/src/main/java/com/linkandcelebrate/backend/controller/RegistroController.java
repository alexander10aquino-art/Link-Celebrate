package com.linkandcelebrate.backend.controller;

import com.linkandcelebrate.backend.service.UsuariosService;
import com.linkandcelebrate.backend.service.UsuariosServiceImplements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistroController {

    @Autowired
    private UsuariosService usuariosService;

    // Inyectamos el servicio que implementa UserDetailsService para cargar los detalles del usuario
    @Autowired
    private UsuariosServiceImplements usuariosServiceImplements;

    // Inyectamos el gestor de autenticación de Spring Security
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(
            @RequestParam(value = "nombreCompleto", required = false) String nombreCompleto,
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam(value = "telefono", required = false) String telefono,
            Model model) {

        // Si el HTML envía 'nombreCompleto' usa ese, si no, usa 'nombre'
        String nombreFinal = (nombreCompleto != null && !nombreCompleto.isEmpty()) ? nombreCompleto : nombre;

        if (nombreFinal == null || nombreFinal.isEmpty()) {
            model.addAttribute("error", "El campo nombre es obligatorio.");
            return "registro";
        }

        if (usuariosService.existeEmail(email)) {
            model.addAttribute("error", "El correo electrónico ya está registrado.");
            return "registro";
        }

        // 1. Guardamos al usuario en la base de datos
        usuariosService.registrarUsuario(nombreFinal, email, password, telefono);

        // 2. AUTO-LOGIN: Autenticamos al usuario inmediatamente después de guardarlo
        try {
            // Cargamos los permisos del usuario usando el email
            UserDetails userDetails = usuariosServiceImplements.loadUserByUsername(email);

            // Creamos el token usando la contraseña plana que acaba de ingresar
            UsernamePasswordAuthenticationToken authReq =
                    new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

            // Autenticamos en Spring Security
            Authentication auth = authenticationManager.authenticate(authReq);
            SecurityContextHolder.getContext().setAuthentication(auth);

            // Redirigimos directamente al catálogo sin pasar por el login
            return "redirect:/catalogo";

        } catch (Exception e) {
            System.out.println("Error al auto-loguear: " + e.getMessage());
            // Si algo falla en el auto-login, lo mandamos al login normal por seguridad
            return "redirect:/login?exitoRegistro";
        }
    }
}