package com.backend.Backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.Backend.myTables.Impiegato;

public interface ImpiegatoRepository extends JpaRepository<Impiegato, Long> {
    // Custom query methods can be defined here if needed
    public Optional<Impiegato> findById(Long id); // Example of a custom query method    
    public Optional<Impiegato> findByEmail(String email); // Example of a custom query method    

}
