package com.linkandcelebrate.backend.controller;

import com.linkandcelebrate.backend.model.Invitaciones;
import com.linkandcelebrate.backend.model.Usuarios;
import com.linkandcelebrate.backend.repository.InvitacionesRepository;
import com.linkandcelebrate.backend.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;

@Controller
public class EventoController {

    @Autowired
    private InvitacionesRepository invitacionesRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @PostMapping("/guardar-evento-vip")
    public String guardarInvitacion(
            @RequestParam("nombreEvento") String nombreEvento,
            @RequestParam("fechaEvento") String fechaEvento,
            @RequestParam("horaEvento") String horaEvento,
            @RequestParam("lugarEvento") String lugarEvento,
            @RequestParam(value = "horaIglesia", required = false) String horaIglesia,
            @RequestParam(value = "lugarIglesia", required = false) String lugarIglesia,
            @RequestParam(value = "mensajeEvento", required = false) String mensajeEvento,
            @RequestParam("plantillaId") String plantillaId,
            @RequestParam("paquete") String paquete,
            Principal principal,
            Model model) {

        // 1. Obtener el usuario autenticado
        String email = principal.getName();
        Usuarios usuario = usuariosRepository.findByGmail(email).orElse(null);

        if (usuario != null) {
            // 2. Guardar la invitación usando únicamente los métodos seguros de tu modelo
            Invitaciones nuevaInvitacion = new Invitaciones();
            nuevaInvitacion.setTituloEvento(nombreEvento);
            nuevaInvitacion.setTipoPaquete(paquete);
            nuevaInvitacion.setEstadoPago("Pendiente");
            nuevaInvitacion.setFkIdUsuario(usuario.getUsuarioId());

            invitacionesRepository.save(nuevaInvitacion);
        }

        // 3. Generar enlace de WhatsApp
        String numeroSoporte = "50236095150";
        String mensaje = "¡Hola equipo de Link & Celebrate! 🥂\n\n" +
                "Acabo de configurar mi evento: *" + nombreEvento + "*.\n" +
                "Elegí la Plantilla #" + plantillaId + " (Paquete " + paquete.toUpperCase() + ").\n\n" +
                "Les escribo para enviarles las fotografías y la canción para mi invitación.";

        String urlWhatsapp = "https://wa.me/" + numeroSoporte + "?text=" + URLEncoder.encode(mensaje, StandardCharsets.UTF_8);

        model.addAttribute("nombreEvento", nombreEvento);
        model.addAttribute("urlWhatsapp", urlWhatsapp);

        return "exito-evento";
    }
}