package com.linkandcelebrate.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PlantillasCatalogoController {

    @GetMapping("/plantillas-catalogo")
    public String cargarPlantillas(Model model) {
        return "plantillas-catalogo"; // Busca src/main/resources/templates/plantillas-catalogo.html
    }
}