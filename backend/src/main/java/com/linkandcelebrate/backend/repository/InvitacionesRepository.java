package com.linkandcelebrate.backend.repository;

import com.linkandcelebrate.backend.model.Invitaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvitacionesRepository extends JpaRepository<Invitaciones, Integer> {

    List<Invitaciones> findByFkIdUsuario(Integer fkIdUsuario);

    Optional<Invitaciones> findBySlugUrl(String slugUrl);
}