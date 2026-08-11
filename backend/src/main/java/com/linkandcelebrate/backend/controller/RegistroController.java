package com.linkandcelebrate.backend.controller;

import com.linkandcelebrate.backend.service.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistroController {

    @Autowired
    private UsuariosService usuariosService;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(
            @RequestParam("nombreCompleto") String nombreCompleto,
            @RequestParam("email") String email,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {

        if (usuariosService.existeUsername(username)) {
            model.addAttribute("error", "El nombre de usuario ya está en uso.");
            return "registro";
        }

        if (usuariosService.existeEmail(email)) {
            model.addAttribute("error", "El correo electrónico ya está registrado.");
            return "registro";
        }

        usuariosService.registrarUsuario(nombreCompleto, email, username, password);

        return "redirect:/login?exitoRegistro";
    }
}