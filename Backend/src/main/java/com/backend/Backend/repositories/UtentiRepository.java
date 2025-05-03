package com.backend.Backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.Backend.myTables.Utente;

public interface UtentiRepository extends JpaRepository<Utente, Long> {//utente è il tipo che rappresenta la tabella e Long è il tipo della chiave primaria
        public Utente findByEmail(String email); //metodo per trovare un utente in base alla sua email

    

}
