package com.linkandcelebrate.backend.validator;

import com.linkandcelebrate.backend.model.Pagos;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PagosValidator {

    public void validar(Pagos pago) {
        if (pago == null) {
            throw new IllegalArgumentException("El registro de pago no puede ser nulo.");
        }
        if (pago.getMonto() == null || pago.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto del pago debe ser mayor a cero.");
        }
        if (pago.getMetodoPago() == null || pago.getMetodoPago().trim().isEmpty()) {
            throw new IllegalArgumentException("El método de pago es obligatorio.");
        }
        if (pago.getFkIdUsuario() == null) {
            throw new IllegalArgumentException("El pago debe estar asociado a un usuario.");
        }
        if (pago.getFkIdInvitacion() == null) {
            throw new IllegalArgumentException("El pago debe estar asociado a una invitación.");
        }
    }
}