package com.backend.Backend.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Backend.myTables.Impiegato;
import com.backend.Backend.myTables.ImpiegatoLavoraOra;
import com.backend.Backend.myTables.ImpiegatoLavoraOraId;
import com.backend.Backend.myTables.OraLavorativa;
import com.backend.Backend.myTables.TipoOra;
import com.backend.Backend.myTables.Utente;
import com.backend.Backend.repositories.ImpiegatoRepository;
import com.backend.Backend.repositories.ManagerRepository;
import com.backend.Backend.repositories.RepositoryAssociazioneImpiegatoOra;
import com.backend.Backend.repositories.RepositoryOra;
import com.backend.Backend.repositories.UtentiRepository;

@Service
public class ServiziOre {
    @Autowired
    private RepositoryOra oraLavorativaRepository;
    @Autowired
    private UtentiRepository utentiRepository;
    @Autowired
    private ImpiegatoRepository impiegatoRepository;
    @Autowired
    private ManagerRepository  managerRepository;
    @Autowired 
    private RepositoryAssociazioneImpiegatoOra associazioneImpiegatoOraRepository;
    public List<OraLavorativa> getAllWorkingHourByMonth(String month) {
        try{
        List<OraLavorativa> allWorkingHours = oraLavorativaRepository.findAll();
        List<OraLavorativa> filteredWorkingHours = new ArrayList<>();
        if(month == null || month.isEmpty()) {
            return allWorkingHours;
        }
        for (OraLavorativa oraLavorativa : allWorkingHours) {
            if (oraLavorativa.getData().toString().contains(month)) {
                filteredWorkingHours.add(oraLavorativa);
            }
        }
        return filteredWorkingHours;
    }
    catch(Exception e){
        System.out.println("Errore nel recupero delle ore lavorative: " + e.getMessage());
        return null;
    }
    }
    public String AssegnaOre(Long Id_Ora,String email,TipoOra tipoOra){
        try{
            OraLavorativa oraLavorativa = oraLavorativaRepository.findById(Id_Ora).orElse(null);
            Impiegato impiegato = impiegatoRepository.findByEmail(email).orElse(null);
            if(impiegato == null){
                return "impiegato non trovato con questa email";
            }
            if(oraLavorativa == null){
                return "Ora lavorativa non trovata";
            }
            ImpiegatoLavoraOra associazioneImpiegatoOra = new ImpiegatoLavoraOra(impiegato, oraLavorativa, tipoOra);
            if(associazioneImpiegatoOraRepository.existsById(new ImpiegatoLavoraOraId(impiegato.getId(), oraLavorativa.getId()))==true){
                return "Associazione già esistente";
            }
            
            associazioneImpiegatoOraRepository.save(associazioneImpiegatoOra);
            return "Ore assegnate con successo";
        }
        catch(Exception e){
            return "Errore nell'assegnazione delle ore: " + e.getMessage();
        }
    }
}
