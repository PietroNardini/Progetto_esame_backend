package com.backend.Backend.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
        @PostMapping("/InsertManager")
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
}
