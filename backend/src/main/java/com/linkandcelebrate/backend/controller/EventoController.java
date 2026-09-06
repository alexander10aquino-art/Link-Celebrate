package com.linkandcelebrate.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class EventoController {

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
            Model model) {

        System.out.println("Nuevo evento recibido: " + nombreEvento);

        // NÚMERO DE WHATSAPP DE SOPORTE
        String numeroSoporte = "50236095150";

        // ARMAR EL MENSAJE AUTOMÁTICO PARA WHATSAPP
        String mensaje = "¡Hola equipo de Link & Celebrate! 🥂\n\n" +
                "Acabo de configurar mi evento: *" + nombreEvento + "*.\n" +
                "Elegí la Plantilla #" + plantillaId + " (Paquete " + paquete.toUpperCase() + ").\n\n" +
                "Les escribo para enviarles las fotografías y la canción para mi invitación.";

        String mensajeCodificado = URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
        String urlWhatsapp = "https://wa.me/" + numeroSoporte + "?text=" + mensajeCodificado;

        // MANDAMOS LOS DATOS A LA PANTALLA DE ÉXITO
        model.addAttribute("nombreEvento", nombreEvento);
        model.addAttribute("urlWhatsapp", urlWhatsapp);

        return "exito-evento";
    }
}