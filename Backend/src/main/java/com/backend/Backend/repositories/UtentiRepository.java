package com.backend.Backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.Backend.myTables.Utente;

public interface UtentiRepository extends JpaRepository<Utente, String> {//utente è il tipo che rappresenta la tabella e String è il tipo della chiave primaria
    

    

}
