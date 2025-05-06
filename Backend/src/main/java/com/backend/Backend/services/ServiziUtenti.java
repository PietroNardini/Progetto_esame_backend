package com.backend.Backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

import com.backend.Backend.myTables.Impiegato;
import com.backend.Backend.myTables.ImpiegatoPagatoOra;
import com.backend.Backend.myTables.ImpiegatoStipendiato;
import com.backend.Backend.myTables.Manager;
import com.backend.Backend.myTables.PasswordResetToken;
import com.backend.Backend.myTables.Utente;
import com.backend.Backend.repositories.ImpiegatoPagatoOraRepository;
import com.backend.Backend.repositories.ImpiegatoStipendiatoRepository;
import com.backend.Backend.repositories.ManagerRepository;
import com.backend.Backend.repositories.RepositoryPasswordResetToken;
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
        private RepositoryPasswordResetToken repositoryPasswordResetToken;
    
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
        public List<Utente> GetManagerByDipartimento(String dipartimento) {
            return managerRepository.findByDipartimento(dipartimento);
        }
        public List<Utente> GetImpiegatoByDipartimento(String dipartimento) {
            List<ImpiegatoPagatoOra> impiegatiPagatiOra = impiegatoPagatoOraRepository.findByDipartimento(dipartimento);
            List<ImpiegatoStipendiato> impiegatiStipendiati = impiegatoStipendiatoRepository.findByDipartimento(dipartimento);
            List<Utente> allImpiegati = new ArrayList<>();

            allImpiegati.addAll(impiegatiPagatiOra);

            allImpiegati.addAll(impiegatiStipendiati);

            return allImpiegati;
        }
        public List<Utente> GetAllImpiegati() {
            List<ImpiegatoPagatoOra> impiegatiPagatiOra = impiegatoPagatoOraRepository.findAll();
            List<ImpiegatoStipendiato> impiegatiStipendiati = impiegatoStipendiatoRepository.findAll();
            List<Utente> allImpiegati = new ArrayList<>();

            allImpiegati.addAll(impiegatiPagatiOra);

            allImpiegati.addAll(impiegatiStipendiati);

            return allImpiegati;
        }
        public Map<String, Object> Login(String email, String password) {
            Utente user = utentiRepository.findByEmail(email);
            if (user == null) {
                    return Map.of("result", "user not found"); // Returning a String for error
            }
            if (!user.verifyPassword(password)) {
                    return Map.of("result", "password isn't correct for this user"); // Returning a String for error
            }
            return Map.of("result", user); // Returning a Persona object for success
    } 
    public Utente GetUtenteByEmail(String email) {
        return utentiRepository.findByEmail(email);
    }
    public void createPasswordResetTokenForUser(Utente user, String token) {        
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        repositoryPasswordResetToken.save(myToken);
    }
public Map<String, Object> ChangePassword(String token, String password) {
        PasswordResetToken request = repositoryPasswordResetToken.findByToken(token);
        if (request == null) {
                return Map.of("result", "wrong token"); // Returning a String for error
        }
        if (request.getExpiryDate().getTime() < System.currentTimeMillis()) {
                System.out.println("Token expired: " + request.getExpiryDate().getTime() + " < " + System.currentTimeMillis());
                repositoryPasswordResetToken.delete(request); 

                return Map.of("result", "request expired"); // Returning a String for error
        }
        Utente user = request.getUser();
        utentiRepository.setPassword(user.getEmail(),Utente.changePassword(password)); 
        repositoryPasswordResetToken.delete(request); 
        user.setPassword(password); // Update the user's password in memory
        return Map.of("result", user); // Returning a Persona object for success
}
}
