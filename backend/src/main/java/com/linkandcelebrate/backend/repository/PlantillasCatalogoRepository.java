package com.linkandcelebrate.backend.repository;

import com.linkandcelebrate.backend.model.PlantillasCatalogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantillasCatalogoRepository extends JpaRepository<PlantillasCatalogo, Integer> {
    List<PlantillasCatalogo> findByCategoria(String categoria);
    List<PlantillasCatalogo> findByTipoPlanRequerido(String tipoPlanRequerido);
}