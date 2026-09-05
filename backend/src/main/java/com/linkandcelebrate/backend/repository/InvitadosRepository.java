package com.linkandcelebrate.backend.repository;

import com.linkandcelebrate.backend.model.Invitados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvitadosRepository extends JpaRepository<Invitados, Integer> {

    List<Invitados> findByFkIdInvitacion(Integer fkIdInvitacion);

    Optional<Invitados> findByNombreInvitado(String nombreInvitado);

    // NUEVO: Método requerido para buscar al invitado por su código QR único
    Optional<Invitados> findByTokenQr(String tokenQr);

    @Query("SELECT COUNT(i) FROM Invitados i WHERE i.fkIdInvitacion = :idInvitacion AND LOWER(i.asistencia) LIKE '%confirmado%'")
    long contarConfirmadosSeguros(@Param("idInvitacion") Integer idInvitacion);

    // =========================================================================
    // NUEVOS MÉTODOS PARA LAS ESTADÍSTICAS DEL DASHBOARD
    // =========================================================================

    // 1. Cuenta el total de invitados registrados para un evento específico
    @Query("SELECT COUNT(i) FROM Invitados i WHERE i.fkIdInvitacion = :invitacionId")
    long countByInvitacionId(@Param("invitacionId") Integer invitacionId);

    // 2. Suma la cantidad de acompañantes de todos los invitados de ese evento
    @Query("SELECT SUM(i.acompanantes) FROM Invitados i WHERE i.fkIdInvitacion = :invitacionId")
    Integer sumarAcompanantes(@Param("invitacionId") Integer invitacionId);
}