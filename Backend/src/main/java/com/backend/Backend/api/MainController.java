package com.backend.Backend.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Backend.myTables.Impiegato;

import com.backend.Backend.myTables.Manager;
import com.backend.Backend.myTables.OraImpiegatoRecord;
import com.backend.Backend.myTables.OraLavorativa;
import com.backend.Backend.myTables.TipoOra;
import com.backend.Backend.myTables.Utente;
import com.backend.Backend.services.ServiziOre;
import com.backend.Backend.services.ServiziUtenti;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

@RestController
/* Indica che questa classe è un controller REST, progettato per gestire richieste HTTP 
   e restituire risposte JSON o altri formati direttamente nel corpo della risposta. */
@RequestMapping("/api")
public class MainController {
        @Autowired
        private ServiziUtenti serviziUtenti; // Servizio per la gestione degli utenti

        @Autowired
        private Environment env; // Ambiente per accedere alle variabili di configurazione

        @Autowired
        private JavaMailSender mailSender; // Servizio per l'invio di email

        @Autowired
        private ServiziOre serviziOra; // Servizio per la gestione delle ore lavorative

        // Metodo per preparare i dati di una lista di utenti in formato mappa
        public  List<Map<String,String>> readyData(List<Utente> utenti) {
            List<Map<String,String>> data = new ArrayList<>();
            for(Utente result : utenti){
                Map<String,String> utentiMap = new HashMap<>();
                utentiMap.put("id", ((Utente)result).getId().toString());
                utentiMap.put("email", ((Utente)result).getEmail());
                utentiMap.put("nome", ((Utente)result).getNome());
                utentiMap.put("cognome", ((Utente)result).getCognome());
                utentiMap.put("telefono", ((Utente)result).getTelefono());
                utentiMap.put("dipartimento", ((Utente)result).getDipartimento());
                utentiMap.put("dataDiNascita", ((Utente)result).getDataDiNascita().toString());
                utentiMap.put("tipo", ((Utente)result).getClass().getSimpleName());
                data.add(utentiMap);
            }
            return data;
        }

        // Metodo per preparare i dati di un singolo utente in formato mappa
        public Map<String,Object> readyData(Utente utente) {
            Map<String,Object> data = new HashMap<>();
            data.put("id", utente.getId());
            data.put("email", utente.getEmail());
            data.put("nome", utente.getNome());
            data.put("cognome", utente.getCognome());
            data.put("telefono", utente.getTelefono());
            data.put("dipartimento", utente.getDipartimento());
            data.put("dataDiNascita", utente.getDataDiNascita().toString());
            data.put("tipo", utente.getClass().getSimpleName());
            return data;
        }

        @GetMapping("/GetAllImpiegati")
        // Endpoint per ottenere tutti gli impiegati
        public List<Map<String,String>> GetAllImpiegati() {
                try{
                    return readyData(serviziUtenti.GetAllImpiegati());
                        
                }
                catch(Exception e){
                        System.out.println(e.getMessage());
                        return null;
                }
        }

        @GetMapping("/GetAllOre")
        // Endpoint per ottenere tutte le ore lavorative
        public List<OraLavorativa> GetAllOre() {
            return null;
        }

        @PostMapping("/GetByDipartimento")
        /* Endpoint per ottenere utenti filtrati per dipartimento e tipo.
           Esempio di chiamata:
           POST http://localhost:8080/api/GetByDipartimento
           {
               "tipo_Utente": "Manager",
               "dipartimento": "IT"
           }
        */
        public List<Map<String,String>> GetByDipartimento(@RequestBody Map<String,String> request) {
            String dipartimento = request.get("dipartimento");
            String tipo = request.get("tipo_Utente");
            if(tipo== null){
                List<Utente> utenti = serviziUtenti.GetManagerByDipartimento(dipartimento);
                utenti.addAll(serviziUtenti.GetImpiegatoByDipartimento(dipartimento));
                return readyData(utenti); 
            }
            try{
                if(tipo.equals("Manager")){
                    return readyData(serviziUtenti.GetManagerByDipartimento(dipartimento));
                }
                else if(tipo.equals("Impiegato")){
                    return readyData(serviziUtenti.GetImpiegatoByDipartimento(dipartimento));
                }
                else{
                    List<Utente> utenti = serviziUtenti.GetManagerByDipartimento(dipartimento);
                    utenti.addAll(serviziUtenti.GetImpiegatoByDipartimento(dipartimento));
                    return readyData(utenti) ;
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                return null;
            }
        }       

        @PostMapping("/InsertManager")
        /* Endpoint per inserire un nuovo manager.
           Esempio di chiamata:
           POST http://localhost:8080/api/InsertManager
           {
               "email": "manager@example.com",
               "password": "securepassword",
               "nome": "Mario",
               "cognome": "Rossi",
               "telefono": "1234567890",
               "dipartimento": "IT",
               "dataDiNascita": "1990-01-01",
               "stipendio": 5000
           }
        */
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
        /* Endpoint per inserire un nuovo impiegato.
           Esempio di chiamata:
           POST http://localhost:8080/api/InsertImpiegato
           {
               "type": "stipendiato",
               "email": "impiegato@example.com",
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

        @PostMapping("/Login")
        /* Endpoint per effettuare il login.
           Esempio di chiamata:
           POST http://localhost:8080/api/Login
           {
               "email": "manager@example.com",
               "password": "securepassword"
           }
        */
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
                    response.put("message", "Login successful");
                    response.put("userData", readyData((Utente)result)); 
                }
            } catch (Exception e) {
                response.put("error", "Failed to login user");
            }
            return ResponseEntity.ok(response);
            
    }

    @PostMapping("/ResetPasswordRequest")
    /* Endpoint per richiedere il reset della password.
       Esempio di chiamata:
       POST http://localhost:8080/api/ResetPasswordRequest
       {
           "email": "email@esempio.com"
       }
    */
    public ResponseEntity<Map<String, String>> requestPasswordReset(HttpServletRequest request ,@RequestBody Map<String, String> requestBody) {
        Map<String, String> response = new HashMap<>();
        String email = requestBody.get("email");
        if (email == null) {
            response.put("error", "Email not provided");
            return ResponseEntity.badRequest().body(response);
        }
        Utente user=serviziUtenti.GetUtenteByEmail(email);
        if (user == null) {
            response.put("error", "User with this email does not exist");
            return ResponseEntity.badRequest().body(response);
        }
        try {
            String token = UUID.randomUUID().toString();
            serviziUtenti.createPasswordResetTokenForUser(user, token);
            mailSender.send(constructResetTokenEmail("http://localhost:4200/", request.getLocale(), token, user));
            response.put("message", "A password reset link has been sent to your email. If you don't see it within a few minutes, please check your spam or junk folder.");
        } catch (Exception e) {
            System.out.println("Error sending email: " + e.getMessage());
            response.put("error", "Failed to send password reset link");
        }
        return ResponseEntity.ok(response);
    }

    private MimeMessage constructResetTokenEmail(String contextPath, Locale locale, String token, Utente user) throws MessagingException {
    // Metodo per costruire l'email di reset della password
    String url = contextPath + "changePassword/" + token;
    String message = String.format("""
        <html>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                <div style="max-width: 600px; margin: auto; background-color: #ffffff; padding: 30px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1);">
                    <h2 style="color: #333333;">Password Reset Request</h2>
                    <p style="font-size: 16px; color: #555555;">
                        We received a request to reset your password. If you made this request, please click the button below:
                    </p>
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="%s"
                           style="background-color: #007bff; color: white; padding: 14px 24px; text-decoration: none; border-radius: 5px; font-size: 16px;">
                            Reset Password
                        </a>
                    </div>
                    <p style="font-size: 14px; color: #888888;">
                        If you did not request a password reset, please ignore this email or contact our support team if you have any concerns.
                    </p>
                    <p style="font-size: 12px; color: #cccccc; margin-top: 30px;">
                        This link will expire in 24 hours for your protection.
                    </p>
                </div>
            </body>
        </html>
        """, url);

    MimeMessage email = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(email, true);
    helper.setSubject("Reset Password");
    helper.setTo(user.getEmail());
    helper.setFrom(env.getProperty("spring.mail.username"));
    helper.setText(message, true); // Set the second parameter to true to indicate HTML content
    return email;
}   

    @PostMapping("/updatePassword")
    /* Endpoint per aggiornare la password.
       Esempio di chiamata:
       POST http://localhost:8080/api/updatePassword
       {
           "token": "token_generato",
           "password": "nuova_password"
       }
    */
    public ResponseEntity<Map<String, Object>> changePassword(@RequestBody Map<String, String> requestBody) {
        Map<String, Object> response = new HashMap<>();
        String token = requestBody.get("token");
        String newPassword = requestBody.get("password");
        try {
            Map<String, Object> loginResult = serviziUtenti.ChangePassword(token, newPassword);
            Object result = loginResult.get("result");
            if (result instanceof String) {
                response.put("message", result); 
            } else if (result instanceof Utente) {
                response.put("message", "Password changed successfully");
                response.put("userData", readyData((Utente)   result)); 
            }
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            System.out.println("Error changing password: " + e.getMessage());
            response.put("error", "Failed to change password");
            return ResponseEntity.badRequest().body(response);
        }
    }
   
    @PostMapping("/GetAllWorkingHours")
    /* Endpoint per ottenere tutte le ore lavorative filtrate per data.
       Esempio di chiamata:
       POST http://localhost:8080/api/GetAllWorkingHours
       {
           "month": "10",
           "day": "15",
           "year": "2023"
       }
    */
    public List<OraLavorativa> GetAllWorkingHours(@RequestBody Map<String,String> request) {
        String month = request.get("month");
        String day = request.get("day");
        String year = request.get("year");
        System.out.println("month: " + month);
        System.out.println("day: " + day);
        System.out.println("year: " + year);
        try{
            return serviziOra.getAllWorkingHours(day,month,year);
        }
        catch(Exception e){
            System.out.println("Errore nel recupero delle ore lavorative: " + e.getMessage());
            return null;
        }
    }
   
    @PostMapping("/AssegnaOre")
    /* Endpoint per assegnare ore lavorative a impiegati.
       Esempio di chiamata:
       POST http://localhost:8080/api/AssegnaOre
       {
           "Lista_Id_Ore": [1, 2, 3],
           "Lista_Id_Impiegati": [1, 7, 9]
       }
    */
    public Map<String, String> AssegnaOre(@RequestBody Map<String, Object> request) {
    Map<String, String> response = new HashMap<>();
    if (request.get("Lista_Id_Ore") == null || request.get("Lista_Id_Impiegati") == null) {
        response.put("error", "Missing required fields");
        return response;
    }
    try {
        @SuppressWarnings("unchecked")
        List<Integer> ids_Ore_AsIntegers = (List<Integer>) request.get("Lista_Id_Ore");
        List<Long> Id_Ore = ids_Ore_AsIntegers.stream()
                                      .map(Integer::longValue) 
                                      .toList();
        @SuppressWarnings("unchecked")
        List<Integer> ids_Impiegati_AsIntegers = (List<Integer>) request.get("Lista_Id_Impiegati");
        List<Long> Ids_Impiegato = ids_Impiegati_AsIntegers.stream()
                            .map(Integer::longValue) 
                            .toList();        
        TipoOra tipoOra = TipoOra.NORMALE;
        if (request.get("tipoOra") != null && request.get("tipoOra").toString().equalsIgnoreCase("straordinario")) {
            tipoOra = TipoOra.STRAORDINARIO;
        }
        String result = "";

        result += "\n" + serviziOra.assegnaOre(Id_Ore, Ids_Impiegato,tipoOra);

        response.put("message", result);
        return response;
    } catch (NumberFormatException e) {
        response.put("error", "Invalid number format for Id_Ora or IDs");
        return response;
    } catch (Exception e) {
        System.out.println(e.getMessage());
        response.put("error", "Failed to assign the hours");
        return response;
    }
    }

    @PostMapping("/GetAllWorkingHoursByImpiegato")
    /* Endpoint per ottenere tutte le ore lavorative di un impiegato.
       Esempio di chiamata:
       POST http://localhost:8080/api/GetAllWorkingHoursByImpiegato
       {
           "Id_Impiegato": "7"
       }
    */
    public List<OraLavorativa> GetAllWorkingHoursByImpiegato(@RequestBody Map<String,String> request) {
        try{
            Long id = Long.parseLong(request.get("Id_Impiegato"));
           
            return serviziOra.getAllWorkingHoursByImpiegato(id);
        }
        catch (NumberFormatException e) {
            System.out.println( "Invalid number format for Id_Ora or IDs");
            return null;
        } 
        catch(Exception e){
            System.out.println("Errore nel recupero delle ore lavorative: " + e.getMessage());
            return null;
        }
    }

    @PostMapping("/RimuoviOreImpiegati")
    /* Endpoint per rimuovere ore lavorative assegnate a impiegati.
       Esempio di chiamata:
       POST http://localhost:8080/api/RimuoviOreImpiegati
       {
           "Lista_Id_Ore": [1, 2, 3],
           "Lista_Id_Impiegati": [1, 7, 9]
       }
    */
    public Map<String, String> RimuoviOre(@RequestBody Map<String, Object> request) {
    Map<String, String> response = new HashMap<>();
    if (request.get("Lista_Id_Ore") == null || request.get("Lista_Id_Impiegati") == null) {
        response.put("error", "Missing required fields");
        return response;
    }
    try {
        @SuppressWarnings("unchecked")
        List<Integer> ids_Ore_AsIntegers = (List<Integer>) request.get("Lista_Id_Ore");
        List<Long> Id_Ore = ids_Ore_AsIntegers.stream()
                                      .map(Integer::longValue) 
                                      .toList();
        @SuppressWarnings("unchecked")
        List<Integer> ids_Impiegati_AsIntegers = (List<Integer>) request.get("Lista_Id_Impiegati");
        List<Long> Ids_Impiegato = ids_Impiegati_AsIntegers.stream()
                            .map(Integer::longValue) 
                            .toList();        
        
        String result = "";
        result += "\n" + serviziOra.rimuoviOrePerImpiegati(Id_Ore, Ids_Impiegato);
        response.put("message", result);
        return response;
    } catch (NumberFormatException e) {
        response.put("error", "Invalid number format for Id_Ora or IDs");
        return response;
    } catch (Exception e) {
        System.out.println(e.getMessage());
        response.put("error", "Failed to assign the hours");
        return response;
    }
    }

    @PatchMapping("/UpdateOre")
    /* Endpoint per aggiornare un'ora lavorativa assegnata a un impiegato.
       Esempio di chiamata:
       PATCH http://localhost:8080/api/UpdateOre
       {
           "Id_Ora": "1",
           "Id_Impiegato": "7",
           "tipoOra": "straordinario"
       }
    */
    public Map<String, String> UpdateOre(@RequestBody Map<String, Object> request) {
        Map<String, String> response = new HashMap<>();
        if (request.get("Id_Ora") == null || request.get("Id_Impiegato") == null) {
            response.put("error", "Missing required fields");
            return response;
        }
        try {
            Long id_Ora = Long.parseLong(request.get("Id_Ora").toString());
            Long id_Impiegato = Long.parseLong(request.get("Id_Impiegato").toString());
            TipoOra tipoOra = TipoOra.NORMALE;
            if (request.get("tipoOra") != null && request.get("tipoOra").toString().equalsIgnoreCase("straordinario")) {
                tipoOra = TipoOra.STRAORDINARIO;
            }            
            String result = serviziOra.UpdateOre(id_Ora, id_Impiegato, tipoOra);
            response.put("message", result);
            return response;
        } catch (NumberFormatException e) {
            response.put("error", "Invalid number format for Id_Ora or IDs");
            return response;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("error", "Failed to assign the hours");
            return response;
        }
    }
    /*
     * Endpoint per ottenere tutti gli impiegati che lavorano in una certa ora lavorativa.
       Esempio di chiamata:
       POST http://localhost:8080/api/GetAllImpiegatiByIdOra
       {
           "Id_Ora": "1"
       }
     */
    @PostMapping("/GetAllImpiegatiByIdOra")
    public List<Impiegato> GetAllImpiegatiByIdOra(@RequestBody Map<String,String> request) {
        try{
            Long id = Long.parseLong(request.get("Id_Ora"));
            return serviziOra.getAllImpiegatiByIdOra(id);
        }
        catch (NumberFormatException e) {
            System.out.println( "Invalid number format for Id_Ora or IDs");
            return null;
        } 
        catch(Exception e){
            System.out.println("Errore nel recupero delle ore lavorative: " + e.getMessage());
            return null;
        }
    }
    @PostMapping("/GetOreImpiegatiPerData")
    /* Endpoint per ottenere ore lavorative e impiegati per una certa data.
       Esempio di chiamata:
       POST http://localhost:8080/api/GetOreImpiegatiPerData
       {
           "data": "2023-10-15"
       }
    */
    public List<OraImpiegatoRecord> GetOreImpiegatiPerData(@RequestBody Map<String, String> request) {
        try {
            String data = request.get("data");
            if (data == null) {
                throw new IllegalArgumentException("Data is required");
            }
            // Converti la stringa in un oggetto LocalDate
            LocalDate parsedDate = LocalDate.parse(data); 
            return serviziOra.getOreImpiegatiPerData(parsedDate); 
        } catch (DateTimeParseException e) {
            System.out.println("Formato data non valido: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Errore nel recupero delle ore lavorative: " + e.getMessage());
            return null;
        }
    }
    @PostMapping("/GetOreImpiegatiPerRange")
    /* Endpoint per ottenere ore lavorative e impiegati per un intervallo di date.
       Esempio di chiamata:
       POST http://localhost:8080/api/GetOreImpiegatiPerWeek
       {
           "start": "2023-10-01",
           "end": "2023-10-07"
       }
    */
    public List<OraImpiegatoRecord> GetOreImpiegatiPerRange(@RequestBody Map<String, String> request) {
        try {
            String start = request.get("start");
            String end = request.get("end");
            if (start == null || end == null) {
                throw new IllegalArgumentException("Start and end dates are required");
            }
            LocalDate parsedStartDate = LocalDate.parse(start);
            LocalDate parsedEndDate = LocalDate.parse(end);
            return serviziOra.getOreImpiegatiPerDataRange(parsedStartDate, parsedEndDate);
        } catch (DateTimeParseException e) {
            System.out.println("Formato data non valido: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Errore nel recupero delle ore lavorative: " + e.getMessage());
            return null;
        }
    }

}
