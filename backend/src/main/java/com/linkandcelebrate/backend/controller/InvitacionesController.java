package com.linkandcelebrate.backend.controller;

import com.linkandcelebrate.backend.model.Invitaciones;
import com.linkandcelebrate.backend.service.InvitacionesService;
import com.linkandcelebrate.backend.validator.InvitacionesValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/invitaciones")
public class InvitacionesController {

    @Autowired
    private InvitacionesService invitacionesService;

    @Autowired
    private InvitacionesValidator invitacionesValidator;

    // 1. Cargar la vista principal con todas las invitaciones
    @GetMapping
    public String cargarInvitaciones(Model model) {
        if (!model.containsAttribute("invitaciones")) {
            model.addAttribute("invitaciones", invitacionesService.getAllInvitaciones());
        }
        return "Invitaciones"; // Plantilla HTML
    }

    // 2. Listar / refrescar la tabla
    @GetMapping("/listar")
    public String listarInvitaciones(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("invitaciones", invitacionesService.getAllInvitaciones());
        redirectAttributes.addFlashAttribute("success", "¡Se actualizó la tabla correctamente!");
        return "redirect:/invitaciones";
    }

    // 3. Buscar invitación por ID
    @GetMapping("/buscar")
    public String buscarInvitacion(RedirectAttributes redirectAttributes, @RequestParam Integer idInvitacion) {
        Invitaciones invitacion = invitacionesService.getInvitacionById(idInvitacion);
        redirectAttributes.addFlashAttribute("invitaciones", List.of(invitacion));
        redirectAttributes.addFlashAttribute("success", "¡Se encontró el registro!");
        return "redirect:/invitaciones";
    }

    // 4. Crear una nueva invitación
    @PostMapping("/crear")
    public String crearInvitacion(RedirectAttributes redirectAttributes,
                                  @Valid @RequestParam String titulo,
                                  @Valid @RequestParam String descripcion,
                                  @Valid @RequestParam String fechaEvento,
                                  @Valid @RequestParam String planTipo, // BASIC, PREMIUM, VIP
                                  @Valid @RequestParam Integer fkIdUsuario,
                                  @Valid @RequestParam Integer fkIdPlantilla) {

        Invitaciones newInvitacion = new Invitaciones();
        newInvitacion.setTitulo(titulo);
        newInvitacion.setDescripcion(descripcion);
        newInvitacion.setFechaEvento(fechaEvento);
        newInvitacion.setPlanTipo(planTipo);
        newInvitacion.setFkIdUsuario(fkIdUsuario);
        newInvitacion.setFkIdPlantilla(fkIdPlantilla);

        invitacionesValidator.validar(newInvitacion);
        invitacionesService.saveInvitacion(newInvitacion);

        redirectAttributes.addFlashAttribute("success", "¡Se creó la invitación correctamente!");
        return "redirect:/invitaciones";
    }

    // 5. Editar una invitación existente
    @PostMapping("/editar")
    public String editarInvitacion(RedirectAttributes redirectAttributes,
                                   @Valid @RequestParam Integer idInvitacion,
                                   @Valid @RequestParam String titulo,
                                   @Valid @RequestParam String descripcion,
                                   @Valid @RequestParam String fechaEvento,
                                   @Valid @RequestParam String planTipo,
                                   @Valid @RequestParam Integer fkIdUsuario,
                                   @Valid @RequestParam Integer fkIdPlantilla) {

        Invitaciones newInvitacion = new Invitaciones();
        newInvitacion.setTitulo(titulo);
        newInvitacion.setDescripcion(descripcion);
        newInvitacion.setFechaEvento(fechaEvento);
        newInvitacion.setPlanTipo(planTipo);
        newInvitacion.setFkIdUsuario(fkIdUsuario);
        newInvitacion.setFkIdPlantilla(fkIdPlantilla);

        invitacionesValidator.validar(newInvitacion);
        invitacionesService.updateInvitacion(idInvitacion, newInvitacion);

        redirectAttributes.addFlashAttribute("success", "¡Se editó la invitación No: " + idInvitacion + "!");
        return "redirect:/invitaciones";
    }

    // 6. Eliminar invitación
    @GetMapping("/eliminar/{id}")
    public String eliminarInvitacion(RedirectAttributes redirectAttributes, @PathVariable Integer id) {
        invitacionesService.deleteInvitacion(id);
        redirectAttributes.addFlashAttribute("success", "¡Se eliminó la invitación correctamente!");
        return "redirect:/invitaciones";
    }
}