package com.linkandcelebrate.backend.repository;

import com.linkandcelebrate.backend.model.Pagos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagosRepository extends JpaRepository<Pagos, Integer> {
    List<Pagos> findByFkIdUsuario(Integer fkIdUsuario);
    List<Pagos> findByFkIdInvitacion(Integer fkIdInvitacion);
}