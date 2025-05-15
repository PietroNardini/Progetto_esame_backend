package com.backend.Backend.repositories;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import com.backend.Backend.myTables.Impiegato;
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
    @Query("SELECT i FROM Impiegato i JOIN ImpiegatoLavoraOra il ON i.id = il.id.idImpiegato WHERE il.id.idOraLavorativa = :idOra")
    List<Impiegato> findAllImpiegatoby(@Param("idOra") Long idOra);//trova tutti gli impiegati che lavorano in una certa ora lavorativa
    /*Uso LEFT JOIN perchè voglio anche le righe in cui non ci sono impiegati assegnati */
    @Query("SELECT o.id, o.data, o.inizio, o.fine, i.id, i.nome, i.cognome, i.email FROM OraLavorativa o LEFT JOIN ImpiegatoLavoraOra il ON o.id = il.id.idOraLavorativa LEFT JOIN Impiegato i ON i.id = il.id.idImpiegato WHERE o.data = :data")
    List<Object[]> findAllOraImpiegatoByData(@Param("data") LocalDate data);

    /*Scelgo i dati che mi interessano da tutte le ore tra start and end , fa il LEFT join per ottenere anche le ore con assegnati 0 impiegati*/
    @Query("""
    SELECT o.id, o.data, o.inizio, o.fine,i.id, i.nome, i.cognome, i.email FROM OraLavorativa o
    LEFT JOIN ImpiegatoLavoraOra il ON o.id = il.id.idOraLavorativa
    LEFT JOIN Impiegato i ON i.id = il.id.idImpiegato
    WHERE o.data BETWEEN :start AND :end
    """)
List<Object[]> findAllOraImpiegatoByDataRange(@Param("start") Date start, @Param("end") Date end);
    @Query("""
        SELECT i 
        FROM ImpiegatoLavoraOra i 
        WHERE i.id.idImpiegato = :idImpiegato 
        AND i.id.idOraLavorativa IN :idOre
    """)
    List<ImpiegatoLavoraOra> findAssociazioniByIdOreAndIdImpiegato(@Param("idOre") List<Long> idOre, @Param("idImpiegato") Long idImpiegato);
}
