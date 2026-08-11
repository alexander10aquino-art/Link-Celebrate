package com.linkandcelebrate.backend.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 1. Manejar recursos no encontrados (ResourceNotFoundException)
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundException(ResourceNotFoundException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/"; // O redirigir a una vista general de error
    }

    // 2. Manejar excepciones de argumentos ilegales o validaciones personalizadas
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Error en los datos ingresados: " + ex.getMessage());
        return "redirect:/";
    }

    // 3. Manejador global para cualquier otra excepción no controlada
    @ExceptionHandler(Exception.class)
    public String handleGlobalException(Exception ex, Model model) {
        model.addAttribute("error", "Ocurrió un error inesperado en el sistema: " + ex.getMessage());
        return "error"; // Nombre de la plantilla error.html en templates
    }
}