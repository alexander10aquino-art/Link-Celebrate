package com.linkandcelebrate.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RsvpResponseDTO {

    private Integer idInvitado;
    private String nombre;
    private String email;
    private String telefono;
    private String estado; // "CONFIRMADO", "PENDIENTE", "RECHAZADO"
    private Integer acompanantes;
    private String restriccionesAlimentarias;
    private String mensajeEspecial;
    private LocalDateTime fechaConfirmacion;
}