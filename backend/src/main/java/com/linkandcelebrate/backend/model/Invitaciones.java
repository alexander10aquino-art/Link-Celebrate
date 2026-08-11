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
    @Column(name = "invitacion_id") // Nombre real en tu BD
    private Integer idInvitacion;

    @Column(name = "usuario_id", nullable = false) // Nombre real en tu BD
    private Integer fkIdUsuario;

    @Column(name = "plantilla_id") // Nombre real en tu BD
    private Integer fkIdPlantilla;

    @Column(name = "titulo_evento", nullable = false, length = 250) // Nombre real en tu BD
    private String titulo;

    @Column(name = "tipo_paquete", nullable = false) // Nombre real en tu BD
    private String planTipo;

    @Column(name = "fecha_evento", nullable = false) // Nombre real en tu BD
    private java.time.LocalDateTime fechaEvento; // O el tipo de fecha que estés usando (Date, LocalDateTime)

    @Column(name = "slug_url", nullable = false, length = 100) // Nombre real en tu BD
    private String slugUrl;

    @Column(name = "estado_pago") // Nombre real en tu BD
    private String estadoPago;

    @Column(name = "fecha_creacion", insertable = false, updatable = false) // Nombre real en tu BD
    private java.time.LocalDateTime fechaCreacion;
}

// IMPORTANTE: ¡BORRA la variable 'descripcion' de esta clase si la tienes, porque NO EXISTE en tu base de datos!