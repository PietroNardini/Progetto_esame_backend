package com.backend.Backend.services;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Backend.myTables.Impiegato;
import com.backend.Backend.myTables.ImpiegatoLavoraOra;
import com.backend.Backend.myTables.ImpiegatoLavoraOraId;
import com.backend.Backend.myTables.OraImpiegatoRecord;
import com.backend.Backend.myTables.OraLavorativa;
import com.backend.Backend.myTables.TipoOra;
import com.backend.Backend.repositories.ImpiegatoRepository;
import com.backend.Backend.repositories.RepositoryAssociazioneImpiegatoOra;
import com.backend.Backend.repositories.RepositoryOra;

@Service
public class ServiziOre {
    @Autowired
    private RepositoryOra oraLavorativaRepository;
    
    @Autowired
    private ImpiegatoRepository impiegatoRepository;
   
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
    /*Funzione assegnamento ore */
    public String assegnaOre(List<Long> idOre, List<Long> idImpiegati, TipoOra tipoOra) {
    try {
        List<Impiegato> impiegati = impiegatoRepository.findAllById(idImpiegati);//trovo tutti gli impiegati in base agli id
        List<OraLavorativa> ore = oraLavorativaRepository.findAllById(idOre);//trovo tutte le ore lavorative in base agli id
        if (impiegati.size() != idImpiegati.size()) {
            return "Alcuni impiegati non sono stati trovati";
        }
        if (ore.size() != idOre.size()) {
            return "Alcune ore lavorative non sono state trovate";
        }
        Set<ImpiegatoLavoraOraId> idDaAssegnare = new HashSet<>();
        for (Impiegato impiegato : impiegati) {
            for (OraLavorativa ora : ore) {
                idDaAssegnare.add(new ImpiegatoLavoraOraId(impiegato.getId(), ora.getId()));//creo le id delle associzioni da creare usando l'id dell'impiegato e l'id dell'ora lavorativa
            }
        }
        List<ImpiegatoLavoraOra> associazioniEsistenti = associazioneImpiegatoOraRepository.findAllById(idDaAssegnare);//trovo tutte le associazioni esistenti
        Set<ImpiegatoLavoraOraId> idEsistenti = associazioniEsistenti.stream()//trovo tutte le associazioni esistenti
                .map(ImpiegatoLavoraOra::getId)
                .collect(Collectors.toSet());
        List<ImpiegatoLavoraOra> nuoveAssociazioni = new ArrayList<>();
        for (Impiegato impiegato : impiegati) {
            for (OraLavorativa ora : ore) {
                ImpiegatoLavoraOraId id = new ImpiegatoLavoraOraId(impiegato.getId(), ora.getId());
                if (!idEsistenti.contains(id)) {//se l'associazione non esiste già
                    nuoveAssociazioni.add(new ImpiegatoLavoraOra(impiegato, ora, tipoOra));//la inserisco nella lista delle nuove associazioni da creare 
                }
            }
        }
        associazioneImpiegatoOraRepository.saveAll(nuoveAssociazioni);//salvo tutte le nuove associazioni       
         return "Assegnate con successo " + nuoveAssociazioni.size() + " ore lavorative";
    } catch (Exception e) {
        return "Errore nell'assegnazione delle ore: " + e.getMessage();
    }
}


    /*Funzione per ottenere tutte le ore lavorative di un impiegato */
    public List<OraLavorativa> getAllWorkingHoursByImpiegato(Long id) {
    try {
        if (!impiegatoRepository.existsById(id)) {
            System.out.println("Impiegato non trovato con questo id");
            return Collections.emptyList();
        }
        return associazioneImpiegatoOraRepository.findOreLavorativeByImpiegatoId(id);
    } catch (Exception e) {     
        System.out.println("Errore nel recupero delle ore lavorative per l'impiegato: " + e.getMessage());
        return Collections.emptyList();
    }
    }
    /*Funzione per rimuovere ore assegnate agli impiegati*/ 
    public String rimuoviOrePerImpiegati(List<Long> idOre, List<Long> idImpiegati) {
            int count = associazioneImpiegatoOraRepository.deleteByImpiegatoIdInAndOraIdIn(idImpiegati, idOre);
            return count + " associazioni rimosse";
    }
    /*Funzione per ottenere le ore assegnate a un impiegato */
    public List<ImpiegatoLavoraOra> OttieniOreAssegnate(List<Long> idOre, Long idImpiegato) {
    try {
        return associazioneImpiegatoOraRepository.findAssociazioniByIdOreAndIdImpiegato(idOre, idImpiegato);
    } catch (Exception e) {
        System.out.println("Errore nel recupero delle ore assegnate: " + e.getMessage());
        return Collections.emptyList();
    }
    }
    /*Funzione per aggiornare il tipo di un ora lavorativa assegnata a un impiegato */
    public String UpdateOre(Long idImpiegato,Long idOraLavorativa,TipoOra tipoOra) {
    try {
        /*Trovo l'associazione con idImpeigato e idOraLavorativa come chiave primaria */
        Optional<ImpiegatoLavoraOra> associazione = associazioneImpiegatoOraRepository.findById(new ImpiegatoLavoraOraId(idOraLavorativa, idImpiegato));
       
        if (associazione.isPresent()) {
            ImpiegatoLavoraOra oraLavorativa = associazione.get();
            oraLavorativa.setTipoOraLavorativa(tipoOra);//aggiorno il tipo di ora lavorativa
            associazioneImpiegatoOraRepository.save(oraLavorativa);//salvo su database
            return "Ore lavorative aggiornate con successo";
        } else {
            return "Associazione non trovata";
        }
    } catch (Exception e) {
        return "Errore nell'aggiornamento delle ore lavorative: " + e.getMessage();
    }
    }
    public List<Impiegato> getAllImpiegatiByIdOra(Long idOra) {
        try {
            return associazioneImpiegatoOraRepository.findAllImpiegatoby(idOra);
        } catch (Exception e) {
            System.out.println("Errore nel recupero degli impiegati per l'ora lavorativa: " + e.getMessage());
            return Collections.emptyList();
        }
    }
    public List<OraImpiegatoRecord> getOreImpiegatiPerData(LocalDate data) {
    List<Object[]> rows = associazioneImpiegatoOraRepository.findAllOraImpiegatoByData(data);
    Map<Long, OraImpiegatoRecord> result = new LinkedHashMap<>();//creo mappa per memorizzare le ore giornaliere e i rispettivi impiegati
    for (Object[] row : rows) {//per ogni riga del risultato della query
        Long oraId = (Long) row[0];
        LocalDate dataOra = ((java.sql.Date) row[1]).toLocalDate(); // Conversione corretta
        LocalTime inizio = (LocalTime) row[2];
        LocalTime fine = (LocalTime) row[3];
        Long impiegatoId = (Long) row[4];
        String nome = (String) row[5];
        String cognome = (String) row[6];
        String email = (String) row[7];

        result.computeIfAbsent(oraId, id -> // se la mappa delle ore giornaliere non contiene l'oraId, la crea
            new OraImpiegatoRecord(oraId, dataOra, inizio, fine, new ArrayList<>())//creo un nuovo record per l'ora lavorativa
        );

        if (impiegatoId != null) {//se esiste almento un impiegato
            // Aggiungo i dati dell'impiegato alla lista dei datiImpiegati
            Map<String, String> impiegatoMap = Map.of(// creo la mappa per ogni impiegato
                "id", String.valueOf(impiegatoId),
                "nome", nome,
                "cognome", cognome,
                "email", email
            );

            result.get(oraId).datiImpiegati().add(impiegatoMap);//aggiungo la mappa alla lista dei datiImpiegati
        }
    }
    return new ArrayList<>(result.values());//restituisco la lista delle ore lavorative con i rispettivi impiegati
    }
    public List<OraImpiegatoRecord> getOreImpiegatiPerDataRange(LocalDate start, LocalDate end) {

    List<Object[]> rows = associazioneImpiegatoOraRepository //ottengo tutte le ore lavorative della settimana e i relativi dati
        .findAllOraImpiegatoByDataRange(Date.valueOf(start), Date.valueOf(end));

    Map<Long, OraImpiegatoRecord> result = new LinkedHashMap<>();

    for (Object[] row : rows) {//ottengo i dati per ogni ora lavorativa
        Long oraId = (Long) row[0];
        LocalDate dataOra = ((java.sql.Date) row[1]).toLocalDate();
        LocalTime inizio = (LocalTime) row[2];
        LocalTime fine = (LocalTime) row[3];
        Long impiegatoId = (Long) row[4];
        String nome = (String) row[5];
        String cognome = (String) row[6];
        String email = (String) row[7];

        result.computeIfAbsent(oraId, id ->//se l'ora non esiste già nella mappa, la creo
            // creo un nuovo record per l'ora lavorativa
            new OraImpiegatoRecord(oraId, dataOra, inizio, fine, new ArrayList<>())
        );

        if (impiegatoId != null) {
            Map<String, String> impiegatoMap = Map.of(
                "id", String.valueOf(impiegatoId),
                "nome", nome,
                "cognome", cognome,
                "email", email
            );
            result.get(oraId).datiImpiegati().add(impiegatoMap);
        }
    }

    return new ArrayList<>(result.values());
    }

}
