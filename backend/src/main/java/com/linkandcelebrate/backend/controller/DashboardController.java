package com.linkandcelebrate.backend.controller;

import com.linkandcelebrate.backend.model.Usuarios;
import com.linkandcelebrate.backend.model.Invitaciones; // Asegúrate de tener este modelo
import com.linkandcelebrate.backend.repository.UsuariosRepository;
import com.linkandcelebrate.backend.repository.InvitacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private InvitacionesRepository invitacionesRepository;

    @GetMapping("/dashboard")
    public String cargarDashboard(Model model, Principal principal) {
        // 1. Obtener el email del usuario logueado en Spring Security
        String email = principal.getName();

        // 2. Buscar al usuario en la BD
        Usuarios usuario = usuariosRepository.findByGmail(email).orElse(null);

        if (usuario != null) {
            model.addAttribute("usuario", usuario);

            // 3. Buscar si tiene invitaciones creadas (asumiendo que tienes este método en el repo)
// Llamamos al método pasándole el ID (Integer), no el objeto completo
            List<Invitaciones> misEventos = invitacionesRepository.findByFkIdUsuario(usuario.getUsuarioId());            if (!misEventos.isEmpty()) {
                // Si tiene eventos, pasamos el primero a la vista
                model.addAttribute("invitacion", misEventos.get(0));
            } else {
                // Si la lista está vacía, enviamos null para mostrar el panel en blanco
                model.addAttribute("invitacion", null);
            }
        }

        return "dashboard"; // Retorna tu archivo dashboard.html
    }
}