package com.linkandcelebrate.backend.controller;

import com.linkandcelebrate.backend.model.Invitaciones;
import com.linkandcelebrate.backend.model.Invitados;
import com.linkandcelebrate.backend.model.Usuarios;
import com.linkandcelebrate.backend.repository.InvitacionesRepository;
import com.linkandcelebrate.backend.repository.InvitadosRepository;
import com.linkandcelebrate.backend.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class InvitadosController {

    @Autowired
    private InvitadosRepository invitadosRepository;

    @Autowired
    private InvitacionesRepository invitacionesRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @GetMapping("/invitados")
    public String mostrarListaInvitados(Model model, Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            Usuarios usuario = usuariosRepository.findByGmail(email).orElse(null);

            if (usuario != null) {
                List<Invitaciones> misEventos = invitacionesRepository.findByFkIdUsuario(usuario.getUsuarioId());

                if (misEventos != null && !misEventos.isEmpty()) {
                    Invitaciones eventoActual = misEventos.get(0);
                    model.addAttribute("invitacion", eventoActual);

                    List<Invitados> lista = invitadosRepository.findByFkIdInvitacion(eventoActual.getIdInvitacion());
                    model.addAttribute("invitados", lista);
                } else {
                    model.addAttribute("invitados", Collections.emptyList());
                }
            }
        }

        model.addAttribute("nuevoInvitado", new Invitados());
        return "invitados";
    }

    @PostMapping("/invitados/guardar")
    public String guardarInvitado(@ModelAttribute("nuevoInvitado") Invitados nuevoInvitado, Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            Usuarios usuario = usuariosRepository.findByGmail(email).orElse(null);

            if (usuario != null) {
                List<Invitaciones> misEventos = invitacionesRepository.findByFkIdUsuario(usuario.getUsuarioId());

                if (misEventos != null && !misEventos.isEmpty()) {
                    Invitaciones eventoActual = misEventos.get(0);
                    nuevoInvitado.setFkIdInvitacion(eventoActual.getIdInvitacion());
                    nuevoInvitado.setAsistencia("Pendiente");

                    // El tokenQr se genera automáticamente gracias al @PrePersist en el modelo.
                    invitadosRepository.save(nuevoInvitado);
                }
            }
        }
        return "redirect:/invitados";
    }

    @GetMapping("/invitados/eliminar/{id}")
    public String eliminarInvitado(@PathVariable Integer id) {
        invitadosRepository.deleteById(id);
        return "redirect:/invitados";
    }

    // =========================================================================
    // VISTA WEB DEL ESCÁNER PARA LA PUERTA
    // =========================================================================
    @GetMapping("/escaner")
    public String mostrarVistaEscaner() {
        return "escaner"; // Abre el archivo escaner.html que creamos
    }

    // =========================================================================
    // ENDPOINT VIP: VALIDAR ACCESO CON QR EN LA PUERTA (CONSUMIDO POR EL ESCÁNER)
    // =========================================================================
    @GetMapping("/api/vip/validar/{token}")
    @ResponseBody
    public ResponseEntity<?> validarPaseQr(@PathVariable String token) {
        // 1. Buscar al invitado mediante el token único
        Invitados invitado = invitadosRepository.findByTokenQr(token).orElse(null);

        if (invitado == null) {
            return ResponseEntity.status(404).body(Map.of(
                    "exito", false,
                    "mensaje", "❌ Pase inválido o no encontrado"
            ));
        }

        // 2. Verificar si ya usó su pase previamente (Control anti-fraude)
        if ("Ingresó".equals(invitado.getAsistencia())) {
            return ResponseEntity.badRequest().body(Map.of(
                    "exito", false,
                    "nombre", invitado.getNombreInvitado(),
                    "mensaje", "⚠️ ATENCIÓN: Este pase ya fue utilizado"
            ));
        }

        // 3. Marcar como ingresado en la base de datos
        invitado.setAsistencia("Ingresó");
        invitadosRepository.save(invitado);

        // 4. Devolver respuesta de éxito para poner la pantalla verde
        return ResponseEntity.ok(Map.of(
                "exito", true,
                "nombre", invitado.getNombreInvitado(),
                "pases", 1 + (invitado.getAcompanantes() != null ? invitado.getAcompanantes() : 0),
                "mensaje", "✅ ¡Acceso Autorizado!"
        ));
    }
}