package com.linkandcelebrate.backend.controller;

import com.linkandcelebrate.backend.model.Invitaciones;
import com.linkandcelebrate.backend.service.InvitacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invitaciones")
@CrossOrigin("*") // Permite conexión con el frontend
public class InvitacionesController {

    @Autowired
    private InvitacionesService invitacionesService;

    @PostMapping
    public ResponseEntity<Invitaciones> guardar(@RequestBody Invitaciones invitacion) {
        // Validación corregida usando getTituloEvento()
        if (invitacion.getTituloEvento() == null || invitacion.getTituloEvento().trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Invitaciones nueva = invitacionesService.saveInvitacion(invitacion);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invitaciones> actualizar(@PathVariable Integer id, @RequestBody Invitaciones invitacion) {
        // Validación corregida usando getTituloEvento()
        if (invitacion.getTituloEvento() == null || invitacion.getTituloEvento().trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Invitaciones actualizada = invitacionesService.updateInvitacion(id, invitacion);
        if (actualizada != null) {
            return new ResponseEntity<>(actualizada, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invitaciones> obtenerPorId(@PathVariable Integer id) {
        Invitaciones invitacion = invitacionesService.getInvitacionById(id);
        if (invitacion != null) {
            return new ResponseEntity<>(invitacion, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Por si necesitas los otros métodos (listar y eliminar)
    /*
    @GetMapping
    public ResponseEntity<List<Invitaciones>> listarTodas() {
        return new ResponseEntity<>(invitacionesService.listarInvitaciones(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        invitacionesService.eliminarInvitacion(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    */
}