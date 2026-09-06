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

        // 1. Obtener el usuario autenticado actualmente
        String email = principal.getName();
        Usuarios usuario = usuariosRepository.findByGmail(email).orElse(null);

        if (usuario != null) {
            // 2. Crear y guardar la invitación real en la base de datos
            Invitaciones nuevaInvitacion = new Invitaciones();
            nuevaInvitacion.setTituloEvento(nombreEvento);
            // Si tienes campos de fecha/lugar adicionales en tu modelo, los puedes setear aquí:
            // nuevaInvitacion.setFechaEvento(fechaEvento);
            // nuevaInvitacion.setLugarEvento(lugarEvento);
            nuevaInvitacion.setTipoPaquete(paquete);
            nuevaInvitacion.setEstadoPago("Pendiente"); // Empieza pendiente hasta que validen su pago/fotos
            nuevaInvitacion.setFkIdUsuario(usuario.getUsuarioId());

            invitacionesRepository.save(nuevaInvitacion);
            System.out.println("¡Evento guardado con éxito en la BD para el usuario: " + email + "!");
        }

        // 3. NÚMERO DE WHATSAPP DE SOPORTE
        String numeroSoporte = "50236095150";

        // 4. ARMAR EL MENSAJE AUTOMÁTICO PARA WHATSAPP
        String mensaje = "¡Hola equipo de Link & Celebrate! 🥂\n\n" +
                "Acabo de configurar mi evento: *" + nombreEvento + "*.\n" +
                "Elegí la Plantilla #" + plantillaId + " (Paquete " + paquete.toUpperCase() + ").\n\n" +
                "Les escribo para enviarles las fotografías y la canción para mi invitación.";

        String mensajeCodificado = URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
        String urlWhatsapp = "https://wa.me/" + numeroSoporte + "?text=" + mensajeCodificado;

        // 5. MANDAMOS LOS DATOS A LA PANTALLA DE ÉXITO
        model.addAttribute("nombreEvento", nombreEvento);
        model.addAttribute("urlWhatsapp", urlWhatsapp);

        return "exito-evento";
    }
}