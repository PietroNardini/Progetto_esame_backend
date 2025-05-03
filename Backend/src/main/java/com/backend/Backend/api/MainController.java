package com.backend.Backend.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Backend.myTables.Impiegato;
import com.backend.Backend.myTables.ImpiegatoPagatoOra;
import com.backend.Backend.myTables.ImpiegatoStipendiato;
import com.backend.Backend.myTables.Manager;
import com.backend.Backend.myTables.OraLavorativa;
import com.backend.Backend.myTables.Utente;
import com.backend.Backend.services.ServiziUtenti;

@RestController
/*Indica che questa classe è un controller REST, progettato per gestire richieste HTTP e restituire risposte JSON o altri formati direttamente nel corpo della risposta. */
@RequestMapping("/api")
public class MainController {
        @Autowired
        private ServiziUtenti serviziUtenti;
        @GetMapping
        public String GetAllImpiegati() {
                return "Hello World!";
        }
        @GetMapping("/GetAllOre")
        public List<OraLavorativa> GetAllOre() {
            return null;
        }

        @PostMapping("/GetByDipartimento")
        /*ESEMPIO CHIAMATA:
         * POST http://localhost:8080/api/GetByDipartimento
        * {
        "tipo_Utente":"Manager",
        "dipartimento": "IT"
        }
        */
        public List<Utente> GetByDipartimento(@RequestBody Map<String,String> request) {
            String dipartimento = request.get("dipartimento");
            String tipo = request.get("tipo_Utente");
            if(tipo== null){
                List<Utente> utenti = serviziUtenti.GetManagerByDipartimento(dipartimento);
                utenti.addAll(serviziUtenti.GetImpiegatoByDipartimento(dipartimento));
                return utenti; 
            }
            try{
                if(tipo.equals("Manager")){
                    return serviziUtenti.GetManagerByDipartimento(dipartimento);
                }
                else if(tipo.equals("Impiegato")){
                    return serviziUtenti.GetImpiegatoByDipartimento(dipartimento);
                }
                else{
                    List<Utente> utenti = serviziUtenti.GetManagerByDipartimento(dipartimento);
                    utenti.addAll(serviziUtenti.GetImpiegatoByDipartimento(dipartimento));
                    return utenti ;
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                return null;
            }
        }       
        @PostMapping("/InsertManager")
        /*{
        "email": "manager2@example2.com",
        "password": "securepassword",
        "nome": "Mario",
        "cognome": "Bianchi",
        "telefono": "1234567890",
        "dipartimento": "HR",
        "dataDiNascita":"1999-01-01",
        "stipendio": 5000
        } */
        public Map<String,String> InsertManager(@RequestBody Manager manager) {
            Map<String, String> response = new HashMap<>();
            try{
                String result = serviziUtenti.InsertManager(manager);
                response.put("message", result);
                return response;
        }
        catch (Exception e) {
            response.put("error", "Failed to save the record");
            return response;
        }
    }
        @PostMapping("/InsertImpiegato")
        /*ESEMPIO CHIAMATA 
         POST http://localhost:8080/api/InsertImpiegato{
            "type": "stipendiato",
            "email": "impiegato2@example.com",
            "password": "securepassword",
            "nome": "Luca",
            "cognome": "Verdi",
            "telefono": "1234567890",
            "dipartimento": "IT",
            "dataDiNascita": "1990-05-15",
            "stipendioMensile": 1500
            }
         */
        public Map<String,String> InsertImpiegato(@RequestBody Impiegato impiegato) {
            Map<String, String> response = new HashMap<>();
            System.out.println(impiegato.getClass());
            try{
                    String result=serviziUtenti.InsertImpiegato(impiegato);
                    response.put("message", result);
                    return response;
            }
        catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("error", "Failed to save the record");
            return response;
        }
    }   
        /*ESEMPIO CHIAMATA 
         * POST http://localhost:8080/api/Login
         * {
            "email":"manager2@example2.com",
            "password": "securepassword"
            }
          */
        @PostMapping("/Login")
        public ResponseEntity<Map<String, Object>> Login (@RequestBody Map<String,String> request) {
            Map<String, Object> response = new HashMap<>();
            String email = request.get("email");
            String password = request.get("password");
            if (email == null || password == null) {
                response.put("error", "Missing required fields");
                return ResponseEntity.badRequest().body(response);
            }
            try {
                Map<String, Object> loginResult = serviziUtenti.Login(email, password);
                Object result = loginResult.get("result");
                if (result instanceof String) {
                    response.put("message", result);
                } else if (result instanceof Utente) {
                    
                    Map<String, Object> userData = new HashMap<>();
                    userData.put("id", ((Utente)result).getId());
                    userData.put("email", ((Utente)result).getEmail());
                    userData.put("nome", ((Utente)result).getNome());
                    userData.put("cognome", ((Utente)result).getCognome());
                    userData.put("telefono", ((Utente)result).getTelefono());
                    userData.put("dipartimento", ((Utente)result).getDipartimento());
                    userData.put("dataDiNascita", ((Utente)result).getDataDiNascita().toString());
                    userData.put("tipo", ((Utente)result).getClass().getSimpleName());
                    response.put("message", "Login successful");
                    response.put("userData", userData); 
                }
            } catch (Exception e) {
                response.put("error", "Failed to login user");
            }
            return ResponseEntity.ok(response);
            
    }
}
