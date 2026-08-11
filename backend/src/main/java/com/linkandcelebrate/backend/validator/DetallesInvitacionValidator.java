package com.linkandcelebrate.backend.validator;

import com.linkandcelebrate.backend.model.DetallesInvitacion;
import org.springframework.stereotype.Component;

@Component
public class DetallesInvitacionValidator {

    public void validar(DetallesInvitacion detalle) {
        if (detalle == null) {
            throw new IllegalArgumentException("El detalle de la invitación no puede ser nulo.");
        }
        if (detalle.getFkIdInvitacion() == null) {
            throw new IllegalArgumentException("Debe especificar el ID de la invitación asociada.");
        }
        if (detalle.getUbicacion() == null || detalle.getUbicacion().trim().isEmpty()) {
            throw new IllegalArgumentException("La ubicación del evento es obligatoria.");
        }
        if (detalle.getHora() == null || detalle.getHora().trim().isEmpty()) {
            throw new IllegalArgumentException("La hora del evento es obligatoria.");
        }
    }
}