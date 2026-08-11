package com.linkandcelebrate.backend.repository;

import com.linkandcelebrate.backend.model.Invitaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitacionesRepository extends JpaRepository<Invitaciones, Integer> {

    // Este nombre coincide EXACTAMENTE con tu campo private Integer fkIdUsuario;
    List<Invitaciones> findByFkIdUsuario(Integer fkIdUsuario);

}