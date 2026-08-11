package com.linkandcelebrate.backend.controller;

import com.linkandcelebrate.backend.model.Invitados;
import com.linkandcelebrate.backend.service.InvitadosService;
import com.linkandcelebrate.backend.validator.InvitadosValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/invitados")
public class InvitadosController {

    @Autowired
    private InvitadosService invitadosService;

    @Autowired
    private InvitadosValidator invitadosValidator;

    // 1. Cargar la vista principal con todos los invitados
    @GetMapping
    public String cargarInvitados(Model model) {
        if (!model.containsAttribute("invitados")) {
            model.addAttribute("invitados", invitadosService.getAllInvitados());
        }
        return "Invitados"; // Plantilla HTML
    }

    // 2. Listar / refrescar la tabla de invitados
    @GetMapping("/listar")
    public String listarInvitados(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("invitados", invitadosService.getAllInvitados());
        redirectAttributes.addFlashAttribute("success", "¡Se actualizó la lista de invitados correctamente!");
        return "redirect:/invitados";
    }

    // 3. Buscar invitado por ID
    @GetMapping("/buscar")
    public String buscarInvitado(RedirectAttributes redirectAttributes, @RequestParam Integer idInvitado) {
        Invitados invitado = invitadosService.getInvitadoById(idInvitado);
        redirectAttributes.addFlashAttribute("invitados", List.of(invitado));
        redirectAttributes.addFlashAttribute("success", "¡Se encontró el registro del invitado!");
        return "redirect:/invitados";
    }

    // 4. Crear / confirmar un nuevo invitado
    @PostMapping("/crear")
    public String crearInvitado(RedirectAttributes redirectAttributes,
                                @Valid @RequestParam String nombre,
                                @Valid @RequestParam String email,
                                @Valid @RequestParam String telefono,
                                @Valid @RequestParam String estado, // CONFIRMADO, PENDIENTE, RECHAZADO
                                @Valid @RequestParam Integer acompanantes,
                                @Valid @RequestParam String restriccionesAlimentarias,
                                @Valid @RequestParam Integer fkIdInvitacion) {

        Invitados newInvitado = new Invitados();
        newInvitado.setNombre(nombre);
        newInvitado.setEmail(email);
        newInvitado.setTelefono(telefono);
        newInvitado.setEstado(estado);
        newInvitado.setAcompanantes(acompanantes);
        newInvitado.setRestriccionesAlimentarias(restriccionesAlimentarias);
        newInvitado.setFkIdInvitacion(fkIdInvitacion);

        invitadosValidator.validar(newInvitado);
        invitadosService.saveInvitado(newInvitado);

        redirectAttributes.addFlashAttribute("success", "¡Se registró el invitado correctamente!");
        return "redirect:/invitados";
    }

    // 5. Editar información de un invitado
    @PostMapping("/editar")
    public String editarInvitado(RedirectAttributes redirectAttributes,
                                 @Valid @RequestParam Integer idInvitado,
                                 @Valid @RequestParam String nombre,
                                 @Valid @RequestParam String email,
                                 @Valid @RequestParam String telefono,
                                 @Valid @RequestParam String estado,
                                 @Valid @RequestParam Integer acompanantes,
                                 @Valid @RequestParam String restriccionesAlimentarias,
                                 @Valid @RequestParam Integer fkIdInvitacion) {

        Invitados newInvitado = new Invitados();
        newInvitado.setNombre(nombre);
        newInvitado.setEmail(email);
        newInvitado.setTelefono(telefono);
        newInvitado.setEstado(estado);
        newInvitado.setAcompanantes(acompanantes);
        newInvitado.setRestriccionesAlimentarias(restriccionesAlimentarias);
        newInvitado.setFkIdInvitacion(fkIdInvitacion);

        invitadosValidator.validar(newInvitado);
        invitadosService.updateInvitado(idInvitado, newInvitado);

        redirectAttributes.addFlashAttribute("success", "¡Se editó la información del invitado No: " + idInvitado + "!");
        return "redirect:/invitados";
    }

    // 6. Eliminar invitado
    @GetMapping("/eliminar/{id}")
    public String eliminarInvitado(RedirectAttributes redirectAttributes, @PathVariable Integer id) {
        invitadosService.deleteInvitado(id);
        redirectAttributes.addFlashAttribute("success", "¡Se eliminó el invitado correctamente!");
        return "redirect:/invitados";
    }
}