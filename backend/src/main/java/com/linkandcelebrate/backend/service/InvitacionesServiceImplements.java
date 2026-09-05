package com.linkandcelebrate.backend.service;

import com.linkandcelebrate.backend.dto.VipDashboardDTO;
import com.linkandcelebrate.backend.model.Invitaciones;
import com.linkandcelebrate.backend.repository.InvitacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvitacionesServiceImplements implements InvitacionesService {

    @Autowired
    private InvitacionesRepository invitacionesRepository;

    @Override
    public Invitaciones saveInvitacion(Invitaciones invitacion) {
        return invitacionesRepository.save(invitacion);
    }

    @Override
    public List<Invitaciones> getAllInvitaciones() {
        // CORREGIDO: Ahora sí busca en MySQL
        return invitacionesRepository.findAll();
    }

    @Override
    public Invitaciones getInvitacionById(Integer id) {
        return invitacionesRepository.findById(id).orElse(null);
    }

    @Override
    public List<Invitaciones> getInvitacionesByUsuarioId(Integer fkIdUsuario) {
        // CORREGIDO: Busca las invitaciones del usuario en MySQL
        return invitacionesRepository.findByFkIdUsuario(fkIdUsuario);
    }

    @Override
    public Invitaciones updateInvitacion(Integer id, Invitaciones invitacion) {
        Invitaciones existente = getInvitacionById(id);
        if (existente != null) {
            // Nombres reales de tu base de datos y modelo
            existente.setTituloEvento(invitacion.getTituloEvento());
            existente.setFechaEvento(invitacion.getFechaEvento());
            existente.setTipoPaquete(invitacion.getTipoPaquete());
            existente.setFkIdUsuario(invitacion.getFkIdUsuario());
            existente.setFkIdPlantilla(invitacion.getFkIdPlantilla());

            return invitacionesRepository.save(existente);
        }
        return null;
    }

    @Override
    public void deleteInvitacion(Integer id) {
        // CORREGIDO: Elimina de MySQL
        invitacionesRepository.deleteById(id);
    }

    @Override
    public VipDashboardDTO getVipDashboardData(Integer idInvitacion) {
        // Por ahora retorna null para pasar la compilación.
        // Aquí luego meteremos la lógica de tus estadísticas.
        return null;
    }

    // Mantenemos estos por si algún controlador antiguo tuyo los sigue usando
    public List<Invitaciones> listarInvitaciones() {
        return invitacionesRepository.findAll();
    }

    public void eliminarInvitacion(Integer id) {
        invitacionesRepository.deleteById(id);
    }
}