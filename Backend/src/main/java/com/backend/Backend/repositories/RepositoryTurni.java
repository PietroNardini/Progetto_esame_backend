package com.backend.Backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.Backend.myTables.TurnoLavorativo;

public interface RepositoryTurni extends JpaRepository<TurnoLavorativo, Long> {
    

}
