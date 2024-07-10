package com.javiera.alke.repository;

import com.javiera.alke.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Integer> {

    @Query("SELECT c FROM Contacto c WHERE c.user.userId = ?1")
    List<Contacto> findByUserId(String userId);

    @Query("SELECT c FROM Contacto c WHERE c.email = ?1")
    Optional<Contacto> findByEmail(String email);

}
