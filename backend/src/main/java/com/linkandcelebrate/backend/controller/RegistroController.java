package com.linkandcelebrate.backend.controller;

import com.linkandcelebrate.backend.service.UsuariosService;
import com.linkandcelebrate.backend.service.UsuariosServiceImplements;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistroController {

    @Autowired
    private UsuariosService usuariosService;

    @Autowired
    private UsuariosServiceImplements usuariosServiceImplements;

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
            HttpServletRequest request,   // <-- Necesario para guardar la sesión
            HttpServletResponse response, // <-- Necesario para guardar la sesión
            Model model) {

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

        // 2. AUTO-LOGIN
        try {
            UserDetails userDetails = usuariosServiceImplements.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken authReq =
                    new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

            Authentication auth = authenticationManager.authenticate(authReq);
            SecurityContextHolder.getContext().setAuthentication(auth);

            // IMPORTANTE: Guardar la sesión explícitamente para que sobreviva a la redirección
            SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
            securityContextRepository.saveContext(SecurityContextHolder.getContext(), request, response);

            return "redirect:/catalogo";

        } catch (Exception e) {
            System.out.println("Error al auto-loguear: " + e.getMessage());
            return "redirect:/login?exitoRegistro";
        }
    }
}