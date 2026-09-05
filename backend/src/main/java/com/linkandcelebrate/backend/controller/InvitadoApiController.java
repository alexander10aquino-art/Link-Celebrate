package com.linkandcelebrate.backend.controller;

import com.linkandcelebrate.backend.model.Invitados;
import com.linkandcelebrate.backend.model.Invitaciones;
import com.linkandcelebrate.backend.service.InvitadosService;
import com.linkandcelebrate.backend.service.InvitacionesService;
import com.linkandcelebrate.backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invitados")
@CrossOrigin(origins = "*") // Permite que Netlify se conecte sin restricciones
public class InvitadoApiController {

    @Autowired
    private InvitadosService invitadosService;

    @Autowired
    private InvitacionesService invitacionesService;

    // 1. ENDPOINT DE LECTURA (Con bloqueo por falta de pago)
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerInvitadoPorId(@PathVariable Integer id) {
        try {
            // Buscamos al invitado
            Invitados invitado = invitadosService.getInvitadoById(id);

            // Buscamos el evento al que pertenece
            Invitaciones evento = invitacionesService.getInvitacionById(invitado.getFkIdInvitacion());

            // BLOQUEO: Si el evento no está pagado, lanzamos error 402
            if (evento == null || !"Pagado".equalsIgnoreCase(evento.getEstadoPago())) {
                return ResponseEntity.status(402).body("{\"error\": \"Servicio suspendido por falta de pago\"}");
            }

            // Si todo está bien, mandamos los datos
            return ResponseEntity.ok(invitado);

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 2. ENDPOINT DE ESCRITURA (También protegido contra pagos pendientes)
    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmarAsistencia(@RequestBody Invitados datosNuevos) {
        try {
            Invitados invitado = invitadosService.getInvitadoById(datosNuevos.getIdInvitado());

            // Verificamos el evento para no dejar que confirmen si está suspendido
            Invitaciones evento = invitacionesService.getInvitacionById(invitado.getFkIdInvitacion());

            if (evento == null || !"Pagado".equalsIgnoreCase(evento.getEstadoPago())) {
                return ResponseEntity.status(402).body("{\"error\": \"Servicio suspendido por falta de pago\"}");
            }

            invitado.setAsistencia("Confirmado");
            invitadosService.saveInvitado(invitado);

            return ResponseEntity.ok("¡Asistencia confirmada con éxito!");

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body("Error: Invitado no encontrado.");
        }
    }
}