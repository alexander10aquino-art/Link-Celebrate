package com.linkandcelebrate.backend.repository;

import com.linkandcelebrate.backend.model.Invitados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitadosRepository extends JpaRepository<Invitados, Integer> {
    List<Invitados> findByFkIdInvitacion(Integer fkIdInvitacion);
    List<Invitados> findByFkIdInvitacionAndEstado(Integer fkIdInvitacion, String estado);
    long countByFkIdInvitacionAndEstado(Integer fkIdInvitacion, String estado);
}