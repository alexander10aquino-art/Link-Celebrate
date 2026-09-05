package com.linkandcelebrate.backend.controller;

import com.linkandcelebrate.backend.model.Usuarios;
import com.linkandcelebrate.backend.model.Invitaciones;
import com.linkandcelebrate.backend.repository.UsuariosRepository;
import com.linkandcelebrate.backend.repository.InvitacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class FormularioController {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private InvitacionesRepository invitacionesRepository;

    @GetMapping("/formulario-creacion")
    public String mostrarFormulario(
            @RequestParam(value = "plantillaId", required = false) String plantillaId,
            @RequestParam(value = "paquete", required = false) String paquete,
            Principal principal,
            Model model,
            RedirectAttributes redirectAttributes) {

        // 1. SEGURIDAD: Si no ha iniciado sesión, lo mandamos al login
        if (principal == null) {
            return "redirect:/login";
        }

        // 2. Buscamos quién es el usuario actual
        String email = principal.getName();
        Usuarios usuario = usuariosRepository.findByGmail(email).orElse(null);

        if (usuario != null) {
            // 3. LÓGICA DE NEGOCIO: ¿Ya tiene una invitación creada?
            List<Invitaciones> misEventos = invitacionesRepository.findByFkIdUsuario(usuario.getUsuarioId());

            if (misEventos != null && !misEventos.isEmpty()) {
                // ¡REBOTE! Lo mandamos a la ruta correcta con el parámetro de error
                return "redirect:/plantillas-catalogo?error=activo";
            }
        }

        // 4. Si pasa la prueba (es su primer evento), le mostramos el formulario normalmente
        model.addAttribute("plantillaId", plantillaId);
        model.addAttribute("paquete", paquete);

        return "formulario-creacion";
    }
}