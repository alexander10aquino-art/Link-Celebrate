package com.linkandcelebrate.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Integer idPago;

    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(name = "fecha_pago", nullable = false, length = 100)
    private String fechaPago;

    @Column(name = "metodo_pago", nullable = false, length = 50)
    private String metodoPago; // "TARJETA", "TRANSFERENCIA", "PAYPAL"

    @Column(name = "estado", nullable = false, length = 50)
    private String estado; // "COMPLETADO", "PENDIENTE", "RECHAZADO"

    @Column(name = "fk_id_usuario", nullable = false)
    private Integer fkIdUsuario;

    @Column(name = "fk_id_invitacion", nullable = false)
    private Integer fkIdInvitacion;
}