package com.backend.Backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.Backend.myTables.Manager;
import com.backend.Backend.myTables.Utente;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    public List<Utente> findByDipartimento(String dipartimento); 
    public List<Utente> findByEmail(String email); 

}
