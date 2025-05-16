package com.backend.Backend.services;

import com.backend.Backend.myTables.OraLavorativa;
import com.backend.Backend.myTables.ImpiegatoLavoraOra;
import com.backend.Backend.myTables.Storico.OraLavorativaStorico;
import com.backend.Backend.myTables.Storico.ImpiegatoLavoraOraStorico;
import com.backend.Backend.repositories.RepositoryAssociazioneImpiegatoOra;
import com.backend.Backend.repositories.RepositoryOra;
import com.backend.Backend.repositories.storico.OraLavorativaStoricoRepository;
import com.backend.Backend.repositories.storico.ImpiegatoLavoraOraStoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServizioArchiviazione {

    @Autowired
    private RepositoryOra oraLavorativaRepository;
    @Autowired
    private RepositoryAssociazioneImpiegatoOra impiegatoLavoraOraRepository;
    @Autowired
    private OraLavorativaStoricoRepository oraLavorativaStoricoRepository;
    @Autowired
    private ImpiegatoLavoraOraStoricoRepository impiegatoLavoraOraStoricoRepository;

    @Transactional
public void archiviaOrePrecedentiAlMeseCorrente() {
    try{
    List<OraLavorativa> daArchiviare = oraLavorativaRepository.findAll();
    List<ImpiegatoLavoraOra> associazioniDaArchiviare=impiegatoLavoraOraRepository.findAll();
    List<ImpiegatoLavoraOraStorico> associazioni_Archivio= associazioniDaArchiviare.stream()
                                                                                    .map(associazione ->{
                                                                                        ImpiegatoLavoraOraStorico i=new ImpiegatoLavoraOraStorico(associazione);
                                                                                        return i;
                                                                                    })
                                                                                    .collect(Collectors.toList());
    List<OraLavorativaStorico> Ore_storico= daArchiviare.stream()
                                                                .map(ora ->{
                                                                OraLavorativaStorico i=new OraLavorativaStorico();
                                                                i.fromOraLavorativa(ora);
                                                                return i;
                                                                })
                                                                .collect(Collectors.toList());
    oraLavorativaStoricoRepository.saveAll(Ore_storico);
    impiegatoLavoraOraStoricoRepository.saveAll(associazioni_Archivio);
    impiegatoLavoraOraRepository.deleteAll(associazioniDaArchiviare);
    oraLavorativaRepository.deleteAll(daArchiviare);
    }
    catch(Exception e){
        System.out.println(e.getMessage());
    }
}
    
        @Transactional
        public void deleteStoricoOlderThanOneYear() {
            LocalDate oneYearAgo = LocalDate.now().minusYears(1);

            // Find all OraLavorativaStorico older than one year
            List<OraLavorativaStorico> oldOre = oraLavorativaStoricoRepository.findByDataBefore(Date.valueOf(oneYearAgo));
            List<Long> oldOraIds = oldOre.stream()
                .map(OraLavorativaStorico::getId)
                .collect(Collectors.toList());

            // Delete all ImpiegatoLavoraOraStorico with those ora IDs
            impiegatoLavoraOraStoricoRepository.deleteById_IdOraLavorativaIn(oldOraIds);

            // Delete the old OraLavorativaStorico
            oraLavorativaStoricoRepository.deleteAll(oldOre);
        }
}
