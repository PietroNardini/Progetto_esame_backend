package com.backend.Backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

import com.backend.Backend.myTables.Impiegato;
import com.backend.Backend.myTables.ImpiegatoPagatoOra;
import com.backend.Backend.myTables.ImpiegatoStipendiato;
import com.backend.Backend.myTables.Manager;
import com.backend.Backend.myTables.Utente;
import com.backend.Backend.repositories.ImpiegatoPagatoOraRepository;
import com.backend.Backend.repositories.ImpiegatoStipendiatoRepository;
import com.backend.Backend.repositories.ManagerRepository;
import com.backend.Backend.repositories.UtentiRepository;

@Service
public class ServiziUtenti {
        @Autowired
        private UtentiRepository utentiRepository;
        @Autowired
        private ManagerRepository managerRepository;
        @Autowired
        private ImpiegatoPagatoOraRepository impiegatoPagatoOraRepository;
        @Autowired
        private ImpiegatoStipendiatoRepository impiegatoStipendiatoRepository;
        public String InsertManager(Manager manager) {
            try{
                managerRepository.save(manager);
                return "Manager inserito con successo";
            }
            catch(DataIntegrityViolationException e){
                return "Un manager con questa email esiste già";
            }
            catch(Exception e){
                return "Errore nell'inserimento del manager: " +e.getClass().getCanonicalName();
            }
        }
        public String InsertImpiegato(Impiegato impiegato) {
            try{
                if(impiegato instanceof ImpiegatoPagatoOra) {
                    impiegatoPagatoOraRepository.save((ImpiegatoPagatoOra) impiegato);
                    return "Impiegato inserito con successo";
                }
                else if(impiegato instanceof ImpiegatoStipendiato) {
                    impiegatoStipendiatoRepository.save((ImpiegatoStipendiato) impiegato);
                    return "Impiegato inserito con successo";
                }
                else {
                    return "Tipo di impiegato non valido";
                }
            }
            catch(DataIntegrityViolationException e){
                return "Un Impiegato con questa email esiste già";
            }
            catch(Exception e){
                return "Errore nell'inserimento dell'impiegato: " +e.getClass().getCanonicalName();
            }
        }
}
