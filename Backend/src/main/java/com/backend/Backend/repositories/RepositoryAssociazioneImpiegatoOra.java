package com.backend.Backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.Backend.myTables.ImpiegatoLavoraOra;
import com.backend.Backend.myTables.ImpiegatoLavoraOraId;

public interface RepositoryAssociazioneImpiegatoOra extends JpaRepository<ImpiegatoLavoraOra,ImpiegatoLavoraOraId> {
    public Optional<ImpiegatoLavoraOra> findById(ImpiegatoLavoraOraId id); // Example of a custom query method 
    public boolean existsById(ImpiegatoLavoraOraId id); // Example of a custom query method 

}
