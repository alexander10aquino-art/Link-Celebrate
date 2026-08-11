package com.linkandcelebrate.backend.repository;

import com.linkandcelebrate.backend.model.Invitaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitacionesRepository extends JpaRepository<Invitaciones, Integer> {
    List<Invitaciones> findByFkIdUsuario(Integer fkIdUsuario);
    List<Invitaciones> findByPlanTipo(String planTipo);
}