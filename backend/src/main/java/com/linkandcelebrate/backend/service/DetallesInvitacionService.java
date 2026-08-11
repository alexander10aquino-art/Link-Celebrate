package com.linkandcelebrate.backend.service;

import com.linkandcelebrate.backend.model.DetallesInvitacion;
import java.util.List;

public interface DetallesInvitacionService {
    List<DetallesInvitacion> getAllDetalles();
    DetallesInvitacion getDetalleById(Integer id);
    DetallesInvitacion getDetalleByInvitacionId(Integer fkIdInvitacion);
    DetallesInvitacion saveDetalle(DetallesInvitacion detalle);
    DetallesInvitacion updateDetalle(Integer id, DetallesInvitacion detalle);
    void deleteDetalle(Integer id);
}