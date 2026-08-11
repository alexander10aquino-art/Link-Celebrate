package com.linkandcelebrate.backend.service;

import com.linkandcelebrate.backend.model.Invitados;
import com.linkandcelebrate.backend.exception.ResourceNotFoundException;
import com.linkandcelebrate.backend.repository.InvitadosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvitadosServiceImplements implements InvitadosService {

    @Autowired
    private InvitadosRepository invitadosRepository;

    @Override
    public List<Invitados> getAllInvitados() {
        return invitadosRepository.findAll();
    }

    @Override
    public Invitados getInvitadoById(Integer id) {
        return invitadosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invitados", "idInvitado", id));
    }

    @Override
    public List<Invitados> getInvitadosByInvitacionId(Integer fkIdInvitacion) {
        return invitadosRepository.findByFkIdInvitacion(fkIdInvitacion);
    }

    @Override
    public Invitados saveInvitado(Invitados invitado) {
        return invitadosRepository.save(invitado);
    }

    @Override
    public Invitados updateInvitado(Integer id, Invitados invitado) {
        Invitados existente = getInvitadoById(id);
        existente.setNombre(invitado.getNombre());
        existente.setEmail(invitado.getEmail());
        existente.setTelefono(invitado.getTelefono());
        existente.setEstado(invitado.getEstado());
        existente.setAcompanantes(invitado.getAcompanantes());
        existente.setRestriccionesAlimentarias(invitado.getRestriccionesAlimentarias());
        existente.setFkIdInvitacion(invitado.getFkIdInvitacion());
        return invitadosRepository.save(existente);
    }

    @Override
    public void deleteInvitado(Integer id) {
        Invitados existente = getInvitadoById(id);
        invitadosRepository.delete(existente);
    }
}