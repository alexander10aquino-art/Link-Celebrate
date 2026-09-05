package com.linkandcelebrate.backend.controller;

import com.linkandcelebrate.backend.model.Invitaciones;
import com.linkandcelebrate.backend.model.Invitados;
import com.linkandcelebrate.backend.repository.InvitacionesRepository;
import com.linkandcelebrate.backend.repository.InvitadosRepository;
import com.linkandcelebrate.backend.service.InvitadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class VistasController {

    @Autowired
    private InvitadosService invitadosService;

    @Autowired
    private InvitacionesRepository invitacionesRepository;

    @Autowired
    private InvitadosRepository invitadosRepository;

    // 1. Mostrar la Lista de Invitados en el panel


    // 2. Mostrar la invitación pública independiente usando su slug (ej. /e/xv-dani)
    @GetMapping("/e/{slugUrl}")
    public String mostrarInvitacionPublica(@PathVariable String slugUrl, Model model) {
        Invitaciones invitacion = invitacionesRepository.findBySlugUrl(slugUrl).orElse(null);

        if (invitacion == null) {
            return "error";
        }

        model.addAttribute("invitacion", invitacion);
        model.addAttribute("nuevoInvitado", new Invitados()); // Prepara el objeto para el formulario RSVP
        return "plantilla";
    }

    // 3. Mostrar la invitación por el nombre específico del invitado
    @GetMapping("/e/invitado/{nombreInvitado}")
    public String mostrarInvitacionPorNombre(@PathVariable String nombreInvitado, Model model) {
        Invitados invitado = invitadosRepository.findByNombreInvitado(nombreInvitado).orElse(null);

        if (invitado == null) {
            return "error";
        }

        model.addAttribute("invitado", invitado);
        return "plantilla";
    }

    // 4. Recibir y guardar la confirmación del formulario de la plantilla
    @PostMapping("/e/{slugUrl}/confirmar")
    public String procesarConfirmacion(@PathVariable String slugUrl,
                                       @ModelAttribute("nuevoInvitado") Invitados nuevoInvitado) {

        Invitaciones invitacion = invitacionesRepository.findBySlugUrl(slugUrl).orElse(null);

        if (invitacion != null) {
            // Vincula el invitado automáticamente al ID de este evento independiente
            nuevoInvitado.setFkIdInvitacion(invitacion.getIdInvitacion());
            invitadosRepository.save(nuevoInvitado);
        }

        // Redirige de vuelta a la misma invitación para mostrar que se envió con éxito
        return "redirect:/e/" + slugUrl + "?confirmado=true";
    }
}