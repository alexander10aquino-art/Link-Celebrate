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

        // CORREGIDO: Solo usamos los setters de las variables que realmente existen
        existente.setNombreInvitado(invitado.getNombreInvitado());
        existente.setTelefono(invitado.getTelefono());
        existente.setAsistencia(invitado.getAsistencia());
        existente.setComentarios(invitado.getComentarios());
        existente.setFkIdInvitacion(invitado.getFkIdInvitacion());

        return invitadosRepository.save(existente);
    }

    @Override
    public void deleteInvitado(Integer id) {
        Invitados existente = getInvitadoById(id);
        invitadosRepository.delete(existente);
    }
}