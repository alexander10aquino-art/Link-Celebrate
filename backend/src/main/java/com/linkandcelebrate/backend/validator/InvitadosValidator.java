package com.linkandcelebrate.backend.validator;

import com.linkandcelebrate.backend.model.Invitados;
import org.springframework.stereotype.Component;

@Component
public class InvitadosValidator {

    public void validar(Invitados invitado) {
        // Validamos usando el nuevo nombre: getNombreInvitado()
        if (invitado.getNombreInvitado() == null || invitado.getNombreInvitado().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del invitado es obligatorio.");
        }

        if (invitado.getFkIdInvitacion() == null) {
            throw new IllegalArgumentException("El ID de la invitación es obligatorio.");
        }
    }
}