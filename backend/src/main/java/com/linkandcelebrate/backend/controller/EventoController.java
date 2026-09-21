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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

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

        String email = principal.getName();
        Usuarios usuario = usuariosRepository.findByGmail(email).orElse(null);

        if (usuario != null) {
            Invitaciones nuevaInvitacion = new Invitaciones();
            nuevaInvitacion.setTituloEvento(nombreEvento);

            // 1. SOLUCIÓN FECHA: Unimos la fecha y hora del formulario en un LocalDateTime
            if (fechaEvento != null && !fechaEvento.trim().isEmpty()) {
                try {
                    LocalDate date = LocalDate.parse(fechaEvento);
                    LocalTime time = LocalTime.MIDNIGHT; // Hora por defecto por si viene vacía

                    if (horaEvento != null && !horaEvento.trim().isEmpty()) {
                        try {
                            time = LocalTime.parse(horaEvento);
                        } catch (Exception ignored) {}
                    }

                    // Aquí usamos LocalDateTime, el tipo exacto que pide tu modelo
                    nuevaInvitacion.setFechaEvento(LocalDateTime.of(date, time));
                } catch (Exception e) {
                    nuevaInvitacion.setFechaEvento(LocalDateTime.now());
                }
            } else {
                nuevaInvitacion.setFechaEvento(LocalDateTime.now());
            }

            // 2. SOLUCIÓN RELACIONES: Asignamos el paquete, usuario y plantilla correctos
            nuevaInvitacion.setTipoPaquete(paquete);
            nuevaInvitacion.setEstadoPago("Pendiente");
            nuevaInvitacion.setFkIdUsuario(usuario.getUsuarioId());

            try {
                nuevaInvitacion.setFkIdPlantilla(Integer.parseInt(plantillaId));
            } catch (Exception e) {
                nuevaInvitacion.setFkIdPlantilla(1);
            }

            // 3. SOLUCIÓN NULLABLE: Generamos un slug temporal basado en el nombre para evitar el error de MySQL
            String slugGenerado = nombreEvento.replaceAll("\\s+", "-").toLowerCase() + "-" + UUID.randomUUID().toString().substring(0, 5);
            nuevaInvitacion.setSlugUrl(slugGenerado);

            // Guardamos el evento final en la base de datos
            invitacionesRepository.save(nuevaInvitacion);
        }

        // WhatsApp Logic
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