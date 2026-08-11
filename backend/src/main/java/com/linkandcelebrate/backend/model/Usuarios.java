package com.linkandcelebrate.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Integer usuarioId;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "gmail", nullable = false, unique = true, length = 100)
    private String gmail;

    @Column(name = "contrasena", nullable = false, length = 100)
    private String contrasena;

    @Column(name = "telefono", nullable = false, length = 100)
    private String telefono;
}