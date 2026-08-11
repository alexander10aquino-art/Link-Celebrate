package com.linkandcelebrate.backend.service;

import com.linkandcelebrate.backend.model.PlantillasCatalogo;
import java.util.List;

public interface PlantillasCatalogoService {
    List<PlantillasCatalogo> getAllPlantillas();
    PlantillasCatalogo getPlantillaById(Integer id);
    List<PlantillasCatalogo> getPlantillasByCategoria(String categoria);
    PlantillasCatalogo savePlantilla(PlantillasCatalogo plantilla);
    PlantillasCatalogo updatePlantilla(Integer id, PlantillasCatalogo plantilla);
    void deletePlantilla(Integer id);
}