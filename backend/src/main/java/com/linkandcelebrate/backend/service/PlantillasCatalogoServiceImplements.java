package com.linkandcelebrate.backend.service;

import com.linkandcelebrate.backend.model.PlantillasCatalogo;
import com.linkandcelebrate.backend.exception.ResourceNotFoundException;
import com.linkandcelebrate.backend.repository.PlantillasCatalogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantillasCatalogoServiceImplements implements PlantillasCatalogoService {

    @Autowired
    private PlantillasCatalogoRepository plantillasRepository;

    @Override
    public List<PlantillasCatalogo> getAllPlantillas() {
        return plantillasRepository.findAll();
    }

    @Override
    public PlantillasCatalogo getPlantillaById(Integer id) {
        return plantillasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PlantillasCatalogo", "idPlantilla", id));
    }

    @Override
    public List<PlantillasCatalogo> getPlantillasByCategoria(String categoria) {
        return plantillasRepository.findByCategoria(categoria);
    }

    @Override
    public PlantillasCatalogo savePlantilla(PlantillasCatalogo plantilla) {
        return plantillasRepository.save(plantilla);
    }

    @Override
    public PlantillasCatalogo updatePlantilla(Integer id, PlantillasCatalogo plantilla) {
        PlantillasCatalogo existente = getPlantillaById(id);
        existente.setNombre(plantilla.getNombre());
        existente.setCategoria(plantilla.getCategoria());
        existente.setPreviewUrl(plantilla.getPreviewUrl());
        existente.setPrecio(plantilla.getPrecio());
        existente.setTipoPlanRequerido(plantilla.getTipoPlanRequerido());
        return plantillasRepository.save(existente);
    }

    @Override
    public void deletePlantilla(Integer id) {
        PlantillasCatalogo existente = getPlantillaById(id);
        plantillasRepository.delete(existente);
    }
}