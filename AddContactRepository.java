package com.javiera.alke.repository;

import com.javiera.alke.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddContactRepository extends JpaRepository<Contacto, Integer> {
    // Puedes agregar métodos específicos si es necesario
}
