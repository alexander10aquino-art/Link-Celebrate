package com.linkandcelebrate.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "invitaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invitaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invitacion_id")
    private Integer idInvitacion;

    @Column(name = "usuario_id", nullable = false)
    private Integer fkIdUsuario;

    @Column(name = "plantilla_id")
    private Integer fkIdPlantilla;

    @Column(name = "titulo_evento", nullable = false, length = 250)
    private String tituloEvento;

    @Column(name = "tipo_paquete", nullable = false)
    private String tipoPaquete;

    @Column(name = "fecha_evento", nullable = false)
    private LocalDateTime fechaEvento;

    @Column(name = "slug_url", nullable = false, length = 100)
    private String slugUrl;

    @Column(name = "estado_pago")
    private String estadoPago;

    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private LocalDateTime fechaCreacion;
}