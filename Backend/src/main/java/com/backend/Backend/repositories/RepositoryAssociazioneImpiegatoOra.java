package com.backend.Backend.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.Backend.myTables.ImpiegatoLavoraOra;
import com.backend.Backend.myTables.ImpiegatoLavoraOraId;
import com.backend.Backend.myTables.OraLavorativa;

import jakarta.transaction.Transactional;

public interface RepositoryAssociazioneImpiegatoOra extends JpaRepository<ImpiegatoLavoraOra,ImpiegatoLavoraOraId> {
    public Optional<ImpiegatoLavoraOra> findById(ImpiegatoLavoraOraId id); // Example of a custom query method 
    public boolean existsById(ImpiegatoLavoraOraId id); // Example of a custom query method 
    List<ImpiegatoLavoraOra> findById_IdImpiegato(Long idImpiegato);
    @Query("SELECT o FROM OraLavorativa o JOIN ImpiegatoLavoraOra i ON o.id = i.id.idOraLavorativa WHERE i.id.idImpiegato = :idImpiegato")
    List<OraLavorativa> findOreLavorativeByImpiegatoId(@Param("idImpiegato") Long idImpiegato);

    @Modifying
    @Transactional
    @Query("DELETE FROM ImpiegatoLavoraOra i WHERE i.id.idImpiegato IN :idImpiegati AND i.id.idOraLavorativa IN :idOre")
    int deleteByImpiegatoIdInAndOraIdIn(@Param("idImpiegati") List<Long> idImpiegati,
                                        @Param("idOre") List<Long> idOre);

    List<ImpiegatoLavoraOra> findAllByIdIn(Set<ImpiegatoLavoraOraId> ids);

}
