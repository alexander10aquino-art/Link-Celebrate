package com.linkandcelebrate.backend.validator;

import com.linkandcelebrate.backend.model.Invitaciones;
import org.springframework.stereotype.Component; // <-- Asegúrate de importar esto

@Component // <-- ¡ESTA ES LA ETIQUETA MÁGICA!
public class InvitacionesValidator {

    public void validar(Invitaciones invitacion) {
        // ... (todo el código que ya tienes adentro se queda exactamente igual)

        if (invitacion.getTitulo() == null || invitacion.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título del evento es obligatorio.");
        }
        // ...
    }
}