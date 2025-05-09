package com.backend.Backend.services;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<OraLavorativa> getAllWorkingHours(String day,String month,String year) {
        try{
        List<OraLavorativa> allWorkingHours = oraLavorativaRepository.findAll();
        if(month == null && day==null && year==null) {//se non è specificato nulla
            return allWorkingHours;
        }
        return allWorkingHours.stream()//scorro tutta la lista utilizzando una stream
            .filter(oraLavorativa -> {//definisco e applico il filtro 
                LocalDate data = oraLavorativa.getLocalDate();//per ogni ora lavorativa prendo la data
                if(month != null && day != null && year != null) {//se sono specificati tutti i parametri
                    return data.getDayOfMonth() == Integer.parseInt(day) && data.getMonthValue() == Integer.parseInt(month) && data.getYear() == Integer.parseInt(year);
                } else if(month != null && year != null) {//se sono specificati solo mese e anno
                    return data.getMonthValue() == Integer.parseInt(month) && data.getYear() == Integer.parseInt(year);
                } else if(day != null && year != null) {//se sono specificati solo giorno e anno
                    return data.getDayOfMonth() == Integer.parseInt(day) && data.getYear() == Integer.parseInt(year);
                } else if(day != null && month != null) {//se sono specificati solo giorno e mese
                    return data.getDayOfMonth() == Integer.parseInt(day) && data.getMonthValue() == Integer.parseInt(month);
                } else if(year != null) {//se è specificato solo l'anno
                    return data.getYear() == Integer.parseInt(year);
                } else if(month != null) {//se è specificato solo il mese
                    return data.getMonthValue() == Integer.parseInt(month);
                } else if(day != null) {//se è specificato solo il giorno
                    return data.getDayOfMonth() == Integer.parseInt(day);
                }
                return false;//se non è specificato nulla
            })
            .collect(Collectors.toList());//colleziono i risultati filtrati in una nuova lista (se è true lo metto nella lista)
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
