package com.linkandcelebrate.backend.repository;

import com.linkandcelebrate.backend.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Integer> {
    Optional<Usuarios> findByGmail(String gmail);
    boolean existsByGmail(String gmail);
}