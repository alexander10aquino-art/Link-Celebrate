package com.linkandcelebrate.backend.controller;

import com.linkandcelebrate.backend.model.Usuarios;
import com.linkandcelebrate.backend.service.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosService usuariosService;

    // 1. Cargar la vista principal con todos los usuarios
    @GetMapping
    public String cargarUsuarios(Model model) {
        if (!model.containsAttribute("usuarios")) {
            model.addAttribute("usuarios", usuariosService.getAllUsuarios());
        }
        return "Usuarios";
    }

    // 2. Listar usuarios
    @GetMapping("/listar")
    public String listarUsuarios(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("usuarios", usuariosService.getAllUsuarios());
        return "redirect:/usuarios";
    }

    // 3. Crear nuevo usuario
    @PostMapping("/crear")
    public String crearUsuario(
            @RequestParam("nombre") String nombre,
            @RequestParam("gmail") String gmail,
            @RequestParam("contrasena") String contrasena,
            @RequestParam(value = "telefono", required = false) String telefono,
            RedirectAttributes redirectAttributes) {

        Usuarios newUsuario = new Usuarios();
        newUsuario.setNombre(nombre);
        newUsuario.setGmail(gmail);
        newUsuario.setContrasena(contrasena);
        newUsuario.setTelefono(telefono != null ? telefono : "+50200000000");

        usuariosService.saveUsuario(newUsuario);
        redirectAttributes.addFlashAttribute("exito", "Usuario creado exitosamente.");
        return "redirect:/usuarios";
    }

    // 4. Actualizar usuario existente
    @PostMapping("/actualizar")
    public String actualizarUsuario(
            @RequestParam("usuarioId") Integer usuarioId,
            @RequestParam("nombre") String nombre,
            @RequestParam("gmail") String gmail,
            @RequestParam(value = "telefono", required = false) String telefono,
            RedirectAttributes redirectAttributes) {

        Usuarios usuarioExistente = usuariosService.getUsuarioById(usuarioId);
        if (usuarioExistente != null) {
            usuarioExistente.setNombre(nombre);
            usuarioExistente.setGmail(gmail);
            if (telefono != null) {
                usuarioExistente.setTelefono(telefono);
            }
            usuariosService.updateUsuario(usuarioExistente);
            redirectAttributes.addFlashAttribute("exito", "Usuario actualizado correctamente.");
        }
        return "redirect:/usuarios";
    }

    // 5. Eliminar usuario
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        usuariosService.deleteUsuario(id);
        redirectAttributes.addFlashAttribute("exito", "Usuario eliminado correctamente.");
        return "redirect:/usuarios";
    }
}