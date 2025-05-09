package com.backend.Backend.api;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.Backend.myTables.Impiegato;

import com.backend.Backend.myTables.Manager;
import com.backend.Backend.myTables.OraLavorativa;
import com.backend.Backend.myTables.TipoOra;
import com.backend.Backend.myTables.Utente;
import com.backend.Backend.services.ServiziOre;
import com.backend.Backend.services.ServiziUtenti;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.Id;
import jakarta.servlet.http.HttpServletRequest;

@RestController
/*Indica che questa classe è un controller REST, progettato per gestire richieste HTTP e restituire risposte JSON o altri formati direttamente nel corpo della risposta. */
@RequestMapping("/api")
public class MainController {
        @Autowired
        private ServiziUtenti serviziUtenti;
        @Autowired
        private Environment env;
        @Autowired
        private JavaMailSender mailSender;
        @Autowired
        private ServiziOre serviziOra;
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
                    response.put("message", "Login successful");
                    response.put("userData", readyData((Utente)result)); 
                }
            } catch (Exception e) {
                response.put("error", "Failed to login user");
            }
            return ResponseEntity.ok(response);
            
    }
    @PostMapping("/ResetPasswordRequest")
    /*ESEMPIO CHIAMATA 
         * POST http://localhost:8080/api/ResetPasswordRequest
         * {
            "email": "email@esempio.com"
            }
    }*/
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
  @PostMapping("/AssegnaOre")
  public Map<String,String> AssegnaOre(@RequestBody Map<String,String> request) {
        Map<String, String> response = new HashMap<>();
        if(request.get("Id_Ora") == null || request.get("email") == null) {
            response.put("error", "Missing required fields");
            return response;
        }
        Long Id_Ora = Long.parseLong(request.get("Id_Ora"));
        String email_Impiegato =request.get("email");
        TipoOra tipoOra=TipoOra.NORMALE;
        if(request.get("tipoOra") !=null){
           if(request.get("tipoOra").equals("straordinario")){
                tipoOra= TipoOra.STRAORDINARIO;
            }
        }
        try{
            String result = serviziOra.AssegnaOre(Id_Ora, email_Impiegato, tipoOra);
            response.put("message", result);
            return response;
        }
        catch (NumberFormatException e) {
            response.put("error", "Invalid number format for Id_Ora or Id_Impiegato");
            return response;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("error", "Failed to save the record");
            return response;
        }
    }
    @PostMapping("/GetAllWorkingHourByMonth")
    public List<OraLavorativa> GetAllWorkingHourByMonth(@RequestBody Map<String,String> request) {
        String month = request.get("month");
        try{
            return serviziOra.getAllWorkingHourByMonth(month);
        }
        catch(Exception e){
            System.out.println("Errore nel recupero delle ore lavorative: " + e.getMessage());
            return null;
        }
    }
}
