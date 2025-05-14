package com.backend.Backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.Backend.myTables.Impiegato;

public interface ImpiegatoRepository extends JpaRepository<Impiegato, Long> {
    public Optional<Impiegato> findByEmail(String email);//trova un impiegato in base alla sua email 

}
