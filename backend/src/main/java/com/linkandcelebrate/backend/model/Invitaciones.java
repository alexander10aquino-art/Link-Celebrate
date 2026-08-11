package com.linkandcelebrate.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "invitaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invitaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_invitacion")
    private Integer idInvitacion;

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_evento", nullable = false, length = 100)
    private String fechaEvento;

    @Column(name = "plan_tipo", nullable = false, length = 50)
    private String planTipo; // "BASIC", "PREMIUM", "VIP"

    @Column(name = "fk_id_usuario", nullable = false)
    private Integer fkIdUsuario;

    @Column(name = "fk_id_plantilla", nullable = false)
    private Integer fkIdPlantilla;
}