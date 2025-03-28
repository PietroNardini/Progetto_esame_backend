package com.backend.Backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.Backend.myTables.Utente;

public interface Utenti extends JpaRepository<Utente, Long> {
    

    

}
