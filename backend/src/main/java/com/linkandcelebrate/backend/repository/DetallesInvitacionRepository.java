package com.linkandcelebrate.backend.repository;

import com.linkandcelebrate.backend.model.DetallesInvitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DetallesInvitacionRepository extends JpaRepository<DetallesInvitacion, Integer> {
    Optional<DetallesInvitacion> findByFkIdInvitacion(Integer fkIdInvitacion);
}