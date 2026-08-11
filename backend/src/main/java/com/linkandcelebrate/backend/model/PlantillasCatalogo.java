package com.linkandcelebrate.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "plantillas_catalogo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlantillasCatalogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plantilla")
    private Integer idPlantilla;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "categoria", nullable = false, length = 50)
    private String categoria; // "BODA", "CUMPLEAÑOS", "XV_AÑOS", "GRADUACION"

    @Column(name = "preview_url", length = 255)
    private String previewUrl;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "tipo_plan_requerido", nullable = false, length = 50)
    private String tipoPlanRequerido; // "BASIC", "PREMIUM", "VIP"
}