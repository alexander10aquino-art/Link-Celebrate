package com.linkandcelebrate.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "invitados")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invitados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_invitado")
    private Integer idInvitado;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "telefono", length = 50)
    private String telefono;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado; // "CONFIRMADO", "PENDIENTE", "RECHAZADO"

    @Column(name = "acompanantes")
    private Integer acompanantes;

    @Column(name = "restricciones_alimentarias", length = 255)
    private String restriccionesAlimentarias;

    @Column(name = "fk_id_invitacion", nullable = false)
    private Integer fkIdInvitacion;
}