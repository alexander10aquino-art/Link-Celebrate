package com.linkandcelebrate.backend.service;

import com.linkandcelebrate.backend.model.Invitados;
import java.util.List;

public interface InvitadosService {
    List<Invitados> getAllInvitados();
    Invitados getInvitadoById(Integer id);
    List<Invitados> getInvitadosByInvitacionId(Integer fkIdInvitacion);
    Invitados saveInvitado(Invitados invitado);
    Invitados updateInvitado(Integer id, Invitados invitado);
    void deleteInvitado(Integer id);
}