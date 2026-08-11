package com.linkandcelebrate.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalles_invitacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetallesInvitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Integer idDetalle;

    @Column(name = "ubicacion", nullable = false, length = 255)
    private String ubicacion;

    @Column(name = "hora", nullable = false, length = 50)
    private String hora;

    @Column(name = "vestimenta", length = 100)
    private String vestimenta;

    @Column(name = "mensaje_especial", columnDefinition = "TEXT")
    private String mensajeEspecial;

    @Column(name = "fk_id_invitacion", nullable = false)
    private Integer fkIdInvitacion;
}