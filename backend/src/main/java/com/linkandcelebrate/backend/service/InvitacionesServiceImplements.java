package com.linkandcelebrate.backend.service;

import com.linkandcelebrate.backend.dto.RsvpResponseDTO;
import com.linkandcelebrate.backend.dto.VipDashboardDTO;
import com.linkandcelebrate.backend.model.Invitaciones;
import com.linkandcelebrate.backend.model.Invitados;
import com.linkandcelebrate.backend.exception.ResourceNotFoundException;
import com.linkandcelebrate.backend.mapper.InvitadosMapper;
import com.linkandcelebrate.backend.repository.InvitacionesRepository;
import com.linkandcelebrate.backend.repository.InvitadosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvitacionesServiceImplements implements InvitacionesService {

    @Autowired
    private InvitacionesRepository invitacionesRepository;

    @Autowired
    private InvitadosRepository invitadosRepository;

    @Autowired
    private InvitadosMapper invitadosMapper;

    @Override
    public List<Invitaciones> getAllInvitaciones() {
        return invitacionesRepository.findAll();
    }

    @Override
    public Invitaciones getInvitacionById(Integer id) {
        return invitacionesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invitaciones", "idInvitacion", id));
    }

    @Override
    public List<Invitaciones> getInvitacionesByUsuarioId(Integer fkIdUsuario) {
        return invitacionesRepository.findByFkIdUsuario(fkIdUsuario);
    }

    @Override
    public Invitaciones saveInvitacion(Invitaciones invitacion) {
        return invitacionesRepository.save(invitacion);
    }

    @Override
    public Invitaciones updateInvitacion(Integer id, Invitaciones invitacion) {
        Invitaciones existente = getInvitacionById(id);
        existente.setTitulo(invitacion.getTitulo());
        // ¡Se eliminó la línea de setDescripcion!
        existente.setFechaEvento(invitacion.getFechaEvento());
        existente.setPlanTipo(invitacion.getPlanTipo());
        existente.setFkIdUsuario(invitacion.getFkIdUsuario());
        existente.setFkIdPlantilla(invitacion.getFkIdPlantilla());
        return invitacionesRepository.save(existente);
    }

    @Override
    public void deleteInvitacion(Integer id) {
        Invitaciones existente = getInvitacionById(id);
        invitacionesRepository.delete(existente);
    }

    @Override
    public VipDashboardDTO getVipDashboardData(Integer idInvitacion) {
        Invitaciones inv = getInvitacionById(idInvitacion);
        List<Invitados> lista = invitadosRepository.findByFkIdInvitacion(idInvitacion);
        List<RsvpResponseDTO> dtoList = invitadosMapper.toDtoList(lista);

        long confirmados = invitadosRepository.countByFkIdInvitacionAndEstado(idInvitacion, "CONFIRMADO");
        long rechazados = invitadosRepository.countByFkIdInvitacionAndEstado(idInvitacion, "RECHAZADO");
        long pendientes = invitadosRepository.countByFkIdInvitacionAndEstado(idInvitacion, "PENDIENTE");

        int totalAcompanantes = lista.stream()
                .filter(i -> "CONFIRMADO".equalsIgnoreCase(i.getEstado()) && i.getAcompanantes() != null)
                .mapToInt(Invitados::getAcompanantes)
                .sum();

        return VipDashboardDTO.builder()
                .idInvitacion(inv.getIdInvitacion())
                .tituloEvento(inv.getTitulo())
                .fechaEvento(inv.getFechaEvento().toString())
                .totalInvitados(lista.size())
                .totalConfirmados((int) confirmados)
                .totalRechazados((int) rechazados)
                .totalPendientes((int) pendientes)
                .totalAcompanantes(totalAcompanantes)
                .listaInvitados(dtoList)
                .build();
    }
}