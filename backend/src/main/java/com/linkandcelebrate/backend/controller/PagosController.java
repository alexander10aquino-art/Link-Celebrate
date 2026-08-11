package com.linkandcelebrate.backend.controller;

import com.linkandcelebrate.backend.model.Pagos;
import com.linkandcelebrate.backend.service.PagosService;
import com.linkandcelebrate.backend.validator.PagosValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/pagos")
public class PagosController {

    @Autowired
    private PagosService pagosService;

    @Autowired
    private PagosValidator pagosValidator;

    // 1. Cargar la vista principal con todos los pagos
    @GetMapping
    public String cargarPagos(Model model) {
        if (!model.containsAttribute("pagos")) {
            model.addAttribute("pagos", pagosService.getAllPagos());
        }
        return "Pagos"; // Plantilla HTML
    }

    // 2. Listar / refrescar la tabla de pagos
    @GetMapping("/listar")
    public String listarPagos(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("pagos", pagosService.getAllPagos());
        redirectAttributes.addFlashAttribute("success", "¡Se actualizó el historial de pagos correctamente!");
        return "redirect:/pagos";
    }

    // 3. Buscar pago por ID
    @GetMapping("/buscar")
    public String buscarPago(RedirectAttributes redirectAttributes, @RequestParam Integer idPago) {
        Pagos pago = pagosService.getPagoById(idPago);
        redirectAttributes.addFlashAttribute("pagos", List.of(pago));
        redirectAttributes.addFlashAttribute("success", "¡Se encontró el registro del pago!");
        return "redirect:/pagos";
    }

    // 4. Registrar un nuevo pago
    @PostMapping("/crear")
    public String crearPago(RedirectAttributes redirectAttributes,
                            @Valid @RequestParam BigDecimal monto,
                            @Valid @RequestParam String fechaPago,
                            @Valid @RequestParam String metodoPago, // TARJETA, TRANSFERENCIA, PAYPAL
                            @Valid @RequestParam String estado,     // COMPLETADO, PENDIENTE, RECHAZADO
                            @Valid @RequestParam Integer fkIdUsuario,
                            @Valid @RequestParam Integer fkIdInvitacion) {

        Pagos newPago = new Pagos();
        newPago.setMonto(monto);
        newPago.setFechaPago(fechaPago);
        newPago.setMetodoPago(metodoPago);
        newPago.setEstado(estado);
        newPago.setFkIdUsuario(fkIdUsuario);
        newPago.setFkIdInvitacion(fkIdInvitacion);

        pagosValidator.validar(newPago);
        pagosService.savePago(newPago);

        redirectAttributes.addFlashAttribute("success", "¡Se registró el pago correctamente!");
        return "redirect:/pagos";
    }

    // 5. Editar información de un pago
    @PostMapping("/editar")
    public String editarPago(RedirectAttributes redirectAttributes,
                             @Valid @RequestParam Integer idPago,
                             @Valid @RequestParam BigDecimal monto,
                             @Valid @RequestParam String fechaPago,
                             @Valid @RequestParam String metodoPago,
                             @Valid @RequestParam String estado,
                             @Valid @RequestParam Integer fkIdUsuario,
                             @Valid @RequestParam Integer fkIdInvitacion) {

        Pagos newPago = new Pagos();
        newPago.setMonto(monto);
        newPago.setFechaPago(fechaPago);
        newPago.setMetodoPago(metodoPago);
        newPago.setEstado(estado);
        newPago.setFkIdUsuario(fkIdUsuario);
        newPago.setFkIdInvitacion(fkIdInvitacion);

        pagosValidator.validar(newPago);
        pagosService.updatePago(idPago, newPago);

        redirectAttributes.addFlashAttribute("success", "¡Se actualizó la información del pago No: " + idPago + "!");
        return "redirect:/pagos";
    }

    // 6. Eliminar registro de pago
    @GetMapping("/eliminar/{id}")
    public String eliminarPago(RedirectAttributes redirectAttributes, @PathVariable Integer id) {
        pagosService.deletePago(id);
        redirectAttributes.addFlashAttribute("success", "¡Se eliminó el registro de pago correctamente!");
        return "redirect:/pagos";
    }
}