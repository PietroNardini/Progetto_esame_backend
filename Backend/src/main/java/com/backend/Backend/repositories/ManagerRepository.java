package com.backend.Backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.Backend.myTables.Manager;
import com.backend.Backend.myTables.Utente;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    public List<Utente> findByDipartimento(String dipartimento); //trova tutti i manager di un dipartimento
    public Optional<Manager> findByEmail(String email); //trova un manager in base alla sua email(che è unica)
    //Optional è un tipo di ritorno che può contenere un valore o essere vuoto, utile per evitare NullPointerException
}
