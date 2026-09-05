package com.linkandcelebrate.backend.mapper; // Verifica que tu paquete sea este

import com.linkandcelebrate.backend.dto.RsvpResponseDTO;
import com.linkandcelebrate.backend.model.Invitados;
import org.springframework.stereotype.Component;

@Component
public class InvitadosMapper {

    public RsvpResponseDTO toDto(Invitados invitado) {
        if (invitado == null) {
            return null;
        }

        return RsvpResponseDTO.builder()
                .idInvitado(invitado.getIdInvitado())
                // Conectamos los campos del DTO con los nombres correctos de tu BD
                .nombre(invitado.getNombreInvitado())
                .estado(invitado.getAsistencia())
                // Estos 3 ya no existen en tu BD, les pasamos null o 0
                .email(null)
                .acompanantes(0)
                .restriccionesAlimentarias(null)
                .build();
    }

    // Por si tienes el método inverso (de DTO a Entidad)
    public Invitados toEntity(RsvpResponseDTO dto) {
        if (dto == null) {
            return null;
        }

        return Invitados.builder()
                .idInvitado(dto.getIdInvitado())
                .nombreInvitado(dto.getNombre())
                .asistencia(dto.getEstado())
                // No mapeamos el resto porque ya no existen en tu tabla
                .build();
    }
}