package com.linkandcelebrate.backend.validator;

import com.linkandcelebrate.backend.model.PlantillasCatalogo;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PlantillasCatalogoValidator {

    public void validar(PlantillasCatalogo plantilla) {
        if (plantilla == null) {
            throw new IllegalArgumentException("La plantilla no puede ser nula.");
        }
        if (plantilla.getNombre() == null || plantilla.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la plantilla es obligatorio.");
        }
        if (plantilla.getCategoria() == null || plantilla.getCategoria().trim().isEmpty()) {
            throw new IllegalArgumentException("La categoría de la plantilla es obligatoria.");
        }
        if (plantilla.getPrecio() == null || plantilla.getPrecio().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio de la plantilla debe ser mayor o igual a cero.");
        }
        if (plantilla.getTipoPlanRequerido() == null || plantilla.getTipoPlanRequerido().trim().isEmpty()) {
            throw new IllegalArgumentException("Debe definir el tipo de plan requerido para la plantilla.");
        }
    }
}