package com.linkandcelebrate.backend.controller;

import com.linkandcelebrate.backend.model.Usuarios;
import com.linkandcelebrate.backend.model.Invitaciones;
import com.linkandcelebrate.backend.repository.UsuariosRepository;
import com.linkandcelebrate.backend.repository.InvitacionesRepository;
import com.linkandcelebrate.backend.repository.InvitadosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private InvitacionesRepository invitacionesRepository;

    @Autowired
    private InvitadosRepository invitadosRepository;

    @GetMapping("/dashboard")
    public String cargarDashboard(Model model, Principal principal, RedirectAttributes redirectAttributes) {
        String email = principal.getName();
        Usuarios usuario = usuariosRepository.findByGmail(email).orElse(null);

        if (usuario != null) {
            model.addAttribute("usuario", usuario);
            List<Invitaciones> misEventos = invitacionesRepository.findByFkIdUsuario(usuario.getUsuarioId());

            // 1. VALIDACIÓN UX: Si no tiene eventos, no entra al dashboard y lo mandamos al catálogo
            if (misEventos == null || misEventos.isEmpty()) {
                redirectAttributes.addFlashAttribute("mensajeAlerta", "¡Aún no tienes un evento activo! Por favor, elige una plantilla para comenzar.");
                return "redirect:/plantillas-catalogo";
            }

            // 2. Si pasa la validación, cargamos sus datos
            Invitaciones invitacionActual = misEventos.get(0);
            model.addAttribute("invitacion", invitacionActual);

            // Estadísticas
            long confirmados = invitadosRepository.contarConfirmadosSeguros(invitacionActual.getIdInvitacion());
            model.addAttribute("confirmados", confirmados);

            long totalInvitados = invitadosRepository.countByInvitacionId(invitacionActual.getIdInvitacion());
            model.addAttribute("totalInvitados", totalInvitados);

            Integer totalAcompanantes = 0;
            try {
                Integer suma = invitadosRepository.sumarAcompanantes(invitacionActual.getIdInvitacion());
                if (suma != null) {
                    totalAcompanantes = suma;
                }
            } catch (Exception e) {
                totalAcompanantes = 0;
            }
            model.addAttribute("totalAcompanantes", totalAcompanantes);

        } else {
            // Si la sesión expiró o el usuario no existe, lo mandamos al login
            return "redirect:/login";
        }

        return "dashboard";
    }
}