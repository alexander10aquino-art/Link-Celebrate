package com.linkandcelebrate.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String mostrarDashboard(Model model) {
        // Aquí pasarás más adelante los eventos e invitados desde la base de datos
        return "dashboard";
    }
}