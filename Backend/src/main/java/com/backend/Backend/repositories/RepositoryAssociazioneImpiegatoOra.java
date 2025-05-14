package com.backend.Backend.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import com.backend.Backend.myTables.ImpiegatoLavoraOra;
import com.backend.Backend.myTables.ImpiegatoLavoraOraId;
import com.backend.Backend.myTables.OraLavorativa;

import jakarta.transaction.Transactional;

public interface RepositoryAssociazioneImpiegatoOra extends JpaRepository<ImpiegatoLavoraOra,ImpiegatoLavoraOraId> {
    public Optional<ImpiegatoLavoraOra> findById(@NonNull ImpiegatoLavoraOraId id); // Trovo l'associazione tra impiegato e ora lavorativa in base alla chiave composta
    public boolean existsById(@NonNull ImpiegatoLavoraOraId id); // Controllo se esiste l'associazione tra impiegato e ora lavorativa in base alla chiave composta 
    List<ImpiegatoLavoraOra> findById_IdImpiegato(Long idImpiegato);// Trovo tutte le associazioni tra impiegato e ora lavorativa in base all'id dell'impiegato
    /*Trova tutte le ore lavorative dove l'idImpiegato è uguale alla parte della chiave composta idImpiegato */
    @Query("SELECT o FROM OraLavorativa o JOIN ImpiegatoLavoraOra i ON o.id = i.id.idOraLavorativa WHERE i.id.idImpiegato = :idImpiegato")
    List<OraLavorativa> findOreLavorativeByImpiegatoId(@Param("idImpiegato") Long idImpiegato);

    @Modifying//Questa annotazione indica che la query modifica il database
    @Transactional//Questa annotazione indica che la query è transazionale
    /*Rimuove l'assegnazione di un ora a un impiegato se le id delle ore degli impiegati corrispondono alla chiave composta dell'associazione e restituisce quante associazioni sono state rimosse */
    @Query("DELETE FROM ImpiegatoLavoraOra i WHERE i.id.idImpiegato IN :idImpiegati AND i.id.idOraLavorativa IN :idOre")
    int deleteByImpiegatoIdInAndOraIdIn(@Param("idImpiegati") List<Long> idImpiegati,
                                        @Param("idOre") List<Long> idOre);
    List<ImpiegatoLavoraOra> findAllByIdIn(Set<ImpiegatoLavoraOraId> ids);//trova tutte le associazioni tra impiegato e ora lavorativa in base alla chiave composta
}
