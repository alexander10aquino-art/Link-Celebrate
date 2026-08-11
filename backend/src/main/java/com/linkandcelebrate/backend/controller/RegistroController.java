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

        usuariosService.registrarUsuario(nombreFinal, email, password, telefono);

        return "redirect:/login?exitoRegistro";
    }
}