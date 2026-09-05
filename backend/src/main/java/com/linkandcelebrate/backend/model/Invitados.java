package com.linkandcelebrate.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID; // <--- Importante para el código QR

@Entity
@Table(name = "invitados")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invitados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invitado_id")
    private Integer idInvitado;

    @Column(name = "invitacion_id", nullable = false)
    private Integer fkIdInvitacion;

    @Column(name = "nombre_invitado", nullable = false, length = 155)
    private String nombreInvitado;

    @Column(name = "telefono", length = 25)
    private String telefono;

    @Column(name = "acompanantes")
    private Integer acompanantes;

    @Column(name = "asistencia")
    private String asistencia;

    @Column(name = "comentarios", columnDefinition = "TEXT")
    private String comentarios;

    // ==========================================
    // NUEVO: CAMPO PARA EL TOKEN SECRETO DEL QR
    // ==========================================
    @Column(name = "token_qr", unique = true, length = 36)
    private String tokenQr;

    @Column(name = "fecha_confirmacion", insertable = false, updatable = false)
    private LocalDateTime fechaConfirmacion;

    // Se ejecuta automáticamente antes de guardar en la BD para generar el código único
    @PrePersist
    public void generarTokenQr() {
        if (this.tokenQr == null) {
            this.tokenQr = UUID.randomUUID().toString();
        }
    }
}