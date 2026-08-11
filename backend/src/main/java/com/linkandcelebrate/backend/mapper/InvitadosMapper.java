package com.linkandcelebrate.backend.mapper;

import com.linkandcelebrate.backend.dto.RsvpResponseDTO;
import com.linkandcelebrate.backend.model.Invitados;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvitadosMapper {

    // Convierte una entidad Invitados a RsvpResponseDTO
    public RsvpResponseDTO toDto(Invitados invitado) {
        if (invitado == null) {
            return null;
        }

        return RsvpResponseDTO.builder()
                .idInvitado(invitado.getIdInvitado())
                .nombre(invitado.getNombre())
                .email(invitado.getEmail())
                .telefono(invitado.getTelefono())
                .estado(invitado.getEstado())
                .acompanantes(invitado.getAcompanantes())
                .restriccionesAlimentarias(invitado.getRestriccionesAlimentarias())
                .build();
    }

    // Convierte una lista de entidades Invitados a una lista de DTOs
    public List<RsvpResponseDTO> toDtoList(List<Invitados> invitadosList) {
        if (invitadosList == null) {
            return List.of();
        }

        return invitadosList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}