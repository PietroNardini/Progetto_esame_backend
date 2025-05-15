package com.backend.Backend.services;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Backend.myTables.Impiegato;
import com.backend.Backend.myTables.ImpiegatoLavoraOra;
import com.backend.Backend.myTables.ImpiegatoPagatoOra;
import com.backend.Backend.myTables.ImpiegatoStipendiato;
import com.backend.Backend.myTables.Manager;
import com.backend.Backend.myTables.OraLavorativa;
import com.backend.Backend.myTables.PasswordResetToken;
import com.backend.Backend.myTables.TipoOra;
import com.backend.Backend.myTables.Utente;
import com.backend.Backend.repositories.ImpiegatoPagatoOraRepository;
import com.backend.Backend.repositories.ImpiegatoRepository;
import com.backend.Backend.repositories.ImpiegatoStipendiatoRepository;
import com.backend.Backend.repositories.ManagerRepository;
import com.backend.Backend.repositories.RepositoryPasswordResetToken;
import com.backend.Backend.repositories.UtentiRepository;

@Service
public class ServiziUtenti {
        /*Inizializzo le repository con l'annotazione @Autowired */
        @Autowired
        private UtentiRepository utentiRepository;
        @Autowired
        private ManagerRepository managerRepository;
        @Autowired
        private ImpiegatoPagatoOraRepository impiegatoPagatoOraRepository;
        @Autowired
        private RepositoryPasswordResetToken repositoryPasswordResetToken;
        @Autowired
        private ImpiegatoRepository impiegatoRepository;
        @Autowired
        private ImpiegatoStipendiatoRepository impiegatoStipendiatoRepository;
        @Autowired
        private ServiziOre serviziOre;
        /*Funzione per Inserimento manager */
        public String InsertManager(Manager manager) {
            try{
                Optional<Manager> existingManager= managerRepository.findByEmail(manager.getEmail());//trova un manager in base alla sua email
                if(existingManager.isPresent()) {
                    return "Un Manager con questa email esiste già";
                }
                managerRepository.save(manager);
                return "Manager inserito con successo";
            }
            catch(Exception e){
                return "Errore nell'inserimento del manager: " +e.getClass().getCanonicalName();
            }
        }
        /*Funzione per Inserimento impiegato */
        public String InsertImpiegato(Impiegato impiegato) {
            try{
                if(impiegatoRepository.findByEmail(impiegato.getEmail()).isPresent()) {//trova un impiegato in base alla sua email
                    return "Un Impiegato con questa email esiste già";
                }
                if(impiegato instanceof ImpiegatoPagatoOra) {//se l'impiegato è pagato a ore (definito smistamento in Impiegato class)
                    impiegatoPagatoOraRepository.save((ImpiegatoPagatoOra) impiegato);
                    return "Impiegato inserito con successo";
                }
                else if(impiegato instanceof ImpiegatoStipendiato) {//se l'impiegato è stipendiato (definito smistamento in Impiegato class)
                    impiegatoStipendiatoRepository.save((ImpiegatoStipendiato) impiegato);
                    return "Impiegato inserito con successo";
                }
                else {
                    return "Tipo di impiegato non valido";
                }
            }
            
            catch(Exception e){
                return "Errore nell'inserimento dell'impiegato: " +e.getMessage();
            }
        
        }
        /*Ottengo tutti i manager per dipartimento */
        public List<Utente> GetManagerByDipartimento(String dipartimento) {
            return managerRepository.findByDipartimento(dipartimento);
        }
        /*Ottengo tutti gli impiegati per dipartimento */

        public List<Utente> GetImpiegatoByDipartimento(String dipartimento) {
            List<ImpiegatoPagatoOra> impiegatiPagatiOra = impiegatoPagatoOraRepository.findByDipartimento(dipartimento);
            List<ImpiegatoStipendiato> impiegatiStipendiati = impiegatoStipendiatoRepository.findByDipartimento(dipartimento);
            List<Utente> allImpiegati = new ArrayList<>();

            allImpiegati.addAll(impiegatiPagatiOra);

            allImpiegati.addAll(impiegatiStipendiati);

            return allImpiegati;
        }
        /*Ottengo tutti gli impiegati */
        public List<Utente> GetAllImpiegati() {
            List<ImpiegatoPagatoOra> impiegatiPagatiOra = impiegatoPagatoOraRepository.findAll();
            List<ImpiegatoStipendiato> impiegatiStipendiati = impiegatoStipendiatoRepository.findAll();
            List<Utente> allImpiegati = new ArrayList<>();

            allImpiegati.addAll(impiegatiPagatiOra);

            allImpiegati.addAll(impiegatiStipendiati);

            return allImpiegati;
        }
        /*Funzione di login */
        public Map<String, Object> Login(String email, String password) {
            Optional<Utente> user = utentiRepository.findByEmail(email);//controlo se l'email esiste nel database
            if (user.isEmpty()) {
                    return Map.of("result", "user not found"); 
            }
            if (!user.get().verifyPassword(password)) {//controllo se la password è corretta
                    return Map.of("result", "password isn't correct for this user"); 
            }
            return Map.of("result", user.get()); // Returning a Persona object for success
    } 
    /*Funzione per ottenere un impiegato in base all'email */
    public Utente GetUtenteByEmail(String email) {
        return utentiRepository.findByEmail(email).orElse(null);
    }
    /*Funzione per creare un token da mandare via email */
    public void createPasswordResetTokenForUser(Utente user, String token) {        
        PasswordResetToken myToken = new PasswordResetToken(token, user);//creo un nuovo token
        repositoryPasswordResetToken.save(myToken);//e lo salvo nel database
    }
public Map<String, Object> ChangePassword(String token, String password) {// Funzione per cambiare la password
        PasswordResetToken request = repositoryPasswordResetToken.findByToken(token);//se il token è corretto
        if (request == null) {
                return Map.of("result", "wrong token"); 
        }
        if (request.getExpiryDate().isBefore(LocalDateTime.now())) { //Elimino il token se è scaduto
                        System.out.println("Token expired: " + request.getExpiryDate() + " < " + LocalDateTime.now());
                        repositoryPasswordResetToken.delete(request);

                        return Map.of("result", "request expired"); 
        }
        Utente user = request.getUser();//recupero l'utente associato al token
        utentiRepository.setPassword(user.getEmail(),Utente.changePassword(password)); //cambio la password nel database
        repositoryPasswordResetToken.delete(request); //rimuovo il token dal database
        user.setPassword(password); //Cambio la password dell'utente
        return Map.of("result", user); // Returning a Persona object for success

    }
    public Map<String,Object> calcolaStipendio(Long idImpiegato,String mese){
        Optional<Impiegato> impiegatoOpt=impiegatoRepository.findById(idImpiegato);
        Map<String, Object> response = new HashMap<>();
        Double Stipendio=0.0;
        if(impiegatoOpt.isPresent()){
            List<OraLavorativa>Ore_Impiegato=serviziOre.getAllWorkingHoursByImpiegato(idImpiegato);
                    List<Long>Ore_Mensili_id=Ore_Impiegato.stream()//scorro tutta la lista utilizzando una stream
                    .filter(oraLavorativa -> {//definisco e applico il filtro 
                    LocalDate data = oraLavorativa.getLocalDate();
                    return data.getMonthValue() == Integer.parseInt(mese) ;
                    })
                    .map(OraLavorativa::getId)
                    .collect(Collectors.toList());
                    List<ImpiegatoLavoraOra> associazioni=serviziOre.OttieniOreAssegnate(Ore_Mensili_id, idImpiegato);
                    Impiegato impiegato= impiegatoOpt.get();
        if(impiegato instanceof ImpiegatoPagatoOra) {//se l'impiegato è pagato a ore (definito smistamento in Impiegato class)
                    ImpiegatoPagatoOra imp=(ImpiegatoPagatoOra) impiegato;
                    Double pagaOraria=imp.getPagaOraria();
                    for (ImpiegatoLavoraOra elem : associazioni) {
                        if (elem.getTipoOraLavorativa() == TipoOra.NORMALE) {
                            Stipendio += pagaOraria; // Increment by pagaOraria for NORMALE
                        } else if (elem.getTipoOraLavorativa() == TipoOra.STRAORDINARIO) {
                            Stipendio += pagaOraria * 1.5; // Increment by pagaOraria * 1.5 for STRAORDINARIO
                        }
                    }
                    response.put("message", "Impiegato Pagato all'ora");
                    response.put("stipendio", Stipendio);
                    return response;
        }
        else if(impiegato instanceof ImpiegatoStipendiato) {//se l'impiegato è stipendiato (definito smistamento in Impiegato class)
                    ImpiegatoStipendiato imp=(ImpiegatoStipendiato) impiegato;
                    Double StipendioMensile=imp.getStipendioMensile();
                    for (ImpiegatoLavoraOra elem : associazioni) {
                        if (elem.getTipoOraLavorativa() == TipoOra.STRAORDINARIO) {
                            Stipendio += 10 * 1.5; 
                        }
                    }
                    response.put("message", "Impiegato Stipendiato");
                    response.put("stipendio", StipendioMensile+Stipendio);
                    return response;
        }
        else{
            response.put("message", "tipo impiegato non riconosciuto");
            response.put("stipendio", 0);
            return response;
        }
        }
        else{
            response.put("message", "Impiegato non trovato con questo id");
            response.put("stipendio", 0);
            return response;
        }
    }
}
