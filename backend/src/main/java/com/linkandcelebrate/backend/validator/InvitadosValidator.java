package com.linkandcelebrate.backend.validator;

import com.linkandcelebrate.backend.model.Invitados;
import org.springframework.stereotype.Component;

@Component
public class InvitadosValidator {

    public void validar(Invitados invitado) {
        if (invitado == null) {
            throw new IllegalArgumentException("El invitado no puede ser nulo.");
        }
        if (invitado.getNombre() == null || invitado.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del invitado es obligatorio.");
        }
        if (invitado.getEstado() == null || invitado.getEstado().trim().isEmpty()) {
            throw new IllegalArgumentException("El estado de confirmación es obligatorio.");
        }
        if (invitado.getAcompanantes() != null && invitado.getAcompanantes() < 0) {
            throw new IllegalArgumentException("El número de acompañantes no puede ser negativo.");
        }
        if (invitado.getFkIdInvitacion() == null) {
            throw new IllegalArgumentException("El invitado debe estar asignado a una invitación.");
        }
    }
}