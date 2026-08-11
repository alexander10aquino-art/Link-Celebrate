package com.linkandcelebrate.backend.service;

import com.linkandcelebrate.backend.dto.VipDashboardDTO;
import com.linkandcelebrate.backend.model.Invitaciones;
import java.util.List;

public interface InvitacionesService {
    List<Invitaciones> getAllInvitaciones();
    Invitaciones getInvitacionById(Integer id);
    List<Invitaciones> getInvitacionesByUsuarioId(Integer fkIdUsuario);
    Invitaciones saveInvitacion(Invitaciones invitacion);
    Invitaciones updateInvitacion(Integer id, Invitaciones invitacion);
    void deleteInvitacion(Integer id);
    VipDashboardDTO getVipDashboardData(Integer idInvitacion);
}