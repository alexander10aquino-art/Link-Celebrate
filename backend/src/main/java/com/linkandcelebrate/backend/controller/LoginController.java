package com.linkandcelebrate.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login"; // Retorna src/main/resources/templates/login.html
    }

    @GetMapping("/catalogo")
    public String mostrarCatalogo() {
        return "plantillas-catalogo"; // Retorna src/main/resources/templates/plantillas-catalogo.html
    }
}