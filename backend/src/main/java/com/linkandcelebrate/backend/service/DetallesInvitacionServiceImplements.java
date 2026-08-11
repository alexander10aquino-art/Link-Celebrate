package com.linkandcelebrate.backend.service;

import com.linkandcelebrate.backend.model.DetallesInvitacion;
import com.linkandcelebrate.backend.exception.ResourceNotFoundException;
import com.linkandcelebrate.backend.repository.DetallesInvitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetallesInvitacionServiceImplements implements DetallesInvitacionService {

    @Autowired
    private DetallesInvitacionRepository detallesRepository;

    @Override
    public List<DetallesInvitacion> getAllDetalles() {
        return detallesRepository.findAll();
    }

    @Override
    public DetallesInvitacion getDetalleById(Integer id) {
        return detallesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DetallesInvitacion", "idDetalle", id));
    }

    @Override
    public DetallesInvitacion getDetalleByInvitacionId(Integer fkIdInvitacion) {
        return detallesRepository.findByFkIdInvitacion(fkIdInvitacion)
                .orElseThrow(() -> new ResourceNotFoundException("DetallesInvitacion", "fkIdInvitacion", fkIdInvitacion));
    }

    @Override
    public DetallesInvitacion saveDetalle(DetallesInvitacion detalle) {
        return detallesRepository.save(detalle);
    }

    @Override
    public DetallesInvitacion updateDetalle(Integer id, DetallesInvitacion detalle) {
        DetallesInvitacion existente = getDetalleById(id);
        existente.setUbicacion(detalle.getUbicacion());
        existente.setHora(detalle.getHora());
        existente.setVestimenta(detalle.getVestimenta());
        existente.setMensajeEspecial(detalle.getMensajeEspecial());
        existente.setFkIdInvitacion(detalle.getFkIdInvitacion());
        return detallesRepository.save(existente);
    }

    @Override
    public void deleteDetalle(Integer id) {
        DetallesInvitacion existente = getDetalleById(id);
        detallesRepository.delete(existente);
    }
}