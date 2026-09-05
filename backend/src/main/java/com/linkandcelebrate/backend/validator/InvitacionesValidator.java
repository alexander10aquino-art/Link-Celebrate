package com.linkandcelebrate.backend.validator;

import com.linkandcelebrate.backend.model.Invitaciones;
import org.springframework.stereotype.Component;

@Component
public class InvitacionesValidator {

    public void validar(Invitaciones invitacion) {
        // Usamos getTituloEvento(), NO getTitulo()
        if (invitacion.getTituloEvento() == null || invitacion.getTituloEvento().trim().isEmpty()) {
            throw new IllegalArgumentException("El título del evento es obligatorio.");
        }
    }
}