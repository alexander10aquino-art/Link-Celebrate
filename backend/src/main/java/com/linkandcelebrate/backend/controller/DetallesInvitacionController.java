package com.linkandcelebrate.backend.controller;

import com.linkandcelebrate.backend.model.DetallesInvitacion;
import com.linkandcelebrate.backend.service.DetallesInvitacionService;
import com.linkandcelebrate.backend.validator.DetallesInvitacionValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/detalles-invitacion")
public class DetallesInvitacionController {

    @Autowired
    private DetallesInvitacionService detallesInvitacionService;

    @Autowired
    private DetallesInvitacionValidator detallesInvitacionValidator;

    // 1. Cargar la vista principal con todos los detalles
    @GetMapping
    public String cargarDetalles(Model model) {
        if (!model.containsAttribute("detalles")) {
            model.addAttribute("detalles", detallesInvitacionService.getAllDetalles());
        }
        return "DetallesInvitacion"; // Plantilla HTML
    }

    // 2. Listar / refrescar la vista
    @GetMapping("/listar")
    public String listarDetalles(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("detalles", detallesInvitacionService.getAllDetalles());
        redirectAttributes.addFlashAttribute("success", "¡Se actualizó la tabla correctamente!");
        return "redirect:/detalles-invitacion";
    }

    // 3. Buscar detalle por ID
    @GetMapping("/buscar")
    public String buscarDetalle(RedirectAttributes redirectAttributes, @RequestParam Integer idDetalle) {
        DetallesInvitacion detalle = detallesInvitacionService.getDetalleById(idDetalle);
        redirectAttributes.addFlashAttribute("detalles", List.of(detalle));
        redirectAttributes.addFlashAttribute("success", "¡Se encontró el registro!");
        return "redirect:/detalles-invitacion";
    }

    // 4. Crear nuevo detalle de invitación
    @PostMapping("/crear")
    public String crearDetalle(RedirectAttributes redirectAttributes,
                               @Valid @RequestParam String ubicacion,
                               @Valid @RequestParam String hora,
                               @Valid @RequestParam String vestimenta,
                               @Valid @RequestParam String mensajeEspecial,
                               @Valid @RequestParam Integer fkIdInvitacion) {

        DetallesInvitacion newDetalle = new DetallesInvitacion();
        newDetalle.setUbicacion(ubicacion);
        newDetalle.setHora(hora);
        newDetalle.setVestimenta(vestimenta);
        newDetalle.setMensajeEspecial(mensajeEspecial);
        newDetalle.setFkIdInvitacion(fkIdInvitacion);

        detallesInvitacionValidator.validar(newDetalle);
        detallesInvitacionService.saveDetalle(newDetalle);

        redirectAttributes.addFlashAttribute("success", "¡Se creó un nuevo registro correctamente!");
        return "redirect:/detalles-invitacion";
    }

    // 5. Editar detalle existente
    @PostMapping("/editar")
    public String editarDetalle(RedirectAttributes redirectAttributes,
                                @Valid @RequestParam Integer idDetalle,
                                @Valid @RequestParam String ubicacion,
                                @Valid @RequestParam String hora,
                                @Valid @RequestParam String vestimenta,
                                @Valid @RequestParam String mensajeEspecial,
                                @Valid @RequestParam Integer fkIdInvitacion) {

        DetallesInvitacion newDetalle = new DetallesInvitacion();
        newDetalle.setUbicacion(ubicacion);
        newDetalle.setHora(hora);
        newDetalle.setVestimenta(vestimenta);
        newDetalle.setMensajeEspecial(mensajeEspecial);
        newDetalle.setFkIdInvitacion(fkIdInvitacion);

        detallesInvitacionValidator.validar(newDetalle);
        detallesInvitacionService.updateDetalle(idDetalle, newDetalle);

        redirectAttributes.addFlashAttribute("success", "¡Se editó el registro No: " + idDetalle + "!");
        return "redirect:/detalles-invitacion";
    }

    // 6. Eliminar detalle
    @GetMapping("/eliminar/{id}")
    public String eliminarDetalle(RedirectAttributes redirectAttributes, @PathVariable Integer id) {
        detallesInvitacionService.deleteDetalle(id);
        redirectAttributes.addFlashAttribute("success", "¡Se eliminó el registro correctamente!");
        return "redirect:/detalles-invitacion";
    }
}