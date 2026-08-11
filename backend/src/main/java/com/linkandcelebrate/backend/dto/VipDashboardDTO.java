package com.linkandcelebrate.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VipDashboardDTO {

    // Información general del evento VIP
    private Integer idInvitacion;
    private String tituloEvento;
    private String fechaEvento;
    private String tokenVip;

    // Métricas y contadores estadísticos para el panel VIP
    private int totalInvitados;
    private int totalConfirmados;
    private int totalRechazados;
    private int totalPendientes;
    private int totalAcompanantes;

    // Lista detallada con las respuestas de los invitados
    private List<RsvpResponseDTO> listaInvitados;
}