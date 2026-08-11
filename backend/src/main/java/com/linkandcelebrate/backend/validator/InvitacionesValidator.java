package com.linkandcelebrate.backend.validator;

import com.linkandcelebrate.backend.model.Invitaciones;
import org.springframework.stereotype.Component;

@Component
public class InvitacionesValidator {

    public void validar(Invitaciones invitacion) {
        if (invitacion == null) {
            throw new IllegalArgumentException("La invitación no puede ser nula.");
        }
        if (invitacion.getTitulo() == null || invitacion.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título del evento es obligatorio.");
        }
        if (invitacion.getFechaEvento() == null || invitacion.getFechaEvento().trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha del evento es obligatoria.");
        }
        if (invitacion.getPlanTipo() == null || invitacion.getPlanTipo().trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de plan es obligatorio.");
        }
        if (invitacion.getFkIdUsuario() == null) {
            throw new IllegalArgumentException("La invitación debe estar asociada a un usuario.");
        }
        if (invitacion.getFkIdPlantilla() == null) {
            throw new IllegalArgumentException("Debe seleccionar una plantilla para la invitación.");
        }
    }
}