package com.backend.Backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.Backend.myTables.Utente;

public interface UtentiRepository extends JpaRepository<Utente, Long> {
    

    

}
