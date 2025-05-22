package com.backend.Backend;

// Import delle classi necessarie per il test, controller, entità, servizi e librerie di testing
import com.backend.Backend.api.MainController;
import com.backend.Backend.myTables.*;
import com.backend.Backend.services.ServiziOre;
import com.backend.Backend.services.ServiziUtenti;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

// Abilita l'uso delle annotazioni Mockito per mocking automatico e injection
@ExtendWith(MockitoExtension.class)
class MainControllerTest {

    // Mock dei servizi dipendenti dal controller per isolare il test
    @Mock
    private ServiziUtenti serviziUtenti;
    @Mock
    private ServiziOre serviziOra;
    @Mock
    private Environment env;
    @Mock
    private JavaMailSender mailSender;

    // Mock di oggetti legati alla richiesta HTTP e mail per eventuali usi
    @Mock
    private HttpServletRequest request;

    @Mock
    private MimeMessage mimeMessage;

    // Inietta i mock sopra nel controller da testare
    @InjectMocks
    private MainController mainController;

    // Test del metodo di login con credenziali valide di un Manager
    @Test
    void testLoginWithManager2Credentials() {
        // Creo un oggetto Manager che simula l'utente atteso come risposta
        Manager testManager = new Manager();
        testManager.setId(6L);
        testManager.setEmail("manager2@example2.com");
        testManager.setPassword("securepassword");
        testManager.setNome("Mario");
        testManager.setCognome("Bianchi");
        testManager.setTelefono("1234567890");
        testManager.setDipartimento("HR");
        testManager.setDataDiNascita(Date.valueOf(LocalDate.of(1999, 1, 1)));
        testManager.setStipendio(5000L); 

        // Creo una mappa che simula la risposta del servizio ServiziUtenti.Login
        Map<String, Object> mockServiceResponse = new HashMap<>();
        mockServiceResponse.put("result", testManager);

        // Configuro il mock per restituire la risposta simulata quando viene chiamato con le credenziali date
        when(serviziUtenti.Login("manager2@example2.com", "securepassword"))
            .thenReturn(mockServiceResponse);

        // Creo la richiesta di login simulata come mappa (email e password)
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", "manager2@example2.com");
        loginRequest.put("password", "securepassword");

        // Invoco il metodo Login del controller con la richiesta simulata
        ResponseEntity<Map<String, Object>> response = mainController.Login(loginRequest);

        // Verifico che la risposta non sia null e abbia status HTTP 200 OK
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        // Estraggo il corpo della risposta e verifico che non sia null
        Map<String, Object> responseBody = response.getBody();
        assertNotNull(responseBody);
        
        // Verifico che il messaggio di risposta sia corretto
        assertEquals("Login successful", responseBody.get("message"));
        
        // Estraggo i dati utente dalla risposta (cast a mappa) e verifico che non siano null
        @SuppressWarnings("unchecked")
        Map<String, Object> userData = (Map<String, Object>) responseBody.get("userData");
        assertNotNull(userData);
        
        // Verifico che tutti i campi dell'utente ritornato corrispondano a quelli del Manager test creato
        assertEquals("Manager", userData.get("tipo"));
        assertEquals("Bianchi", userData.get("cognome"));
        assertEquals("HR", userData.get("dipartimento"));
        assertEquals("Mario", userData.get("nome"));
        assertEquals(6L, userData.get("id")); 
        assertEquals("1234567890", userData.get("telefono"));
        assertEquals("manager2@example2.com", userData.get("email"));
        assertEquals("1999-01-01", userData.get("dataDiNascita"));
    }
   @Test
    void testInsertManagerIfNotExists() {
        Manager testManager = new Manager();
        testManager.setId(6L);
        testManager.setEmail("manager2@example2.com");
        testManager.setPassword("securepassword");
        testManager.setNome("Mario");
        testManager.setCognome("Bianchi");
        testManager.setTelefono("1234567890");
        testManager.setDipartimento("HR");
        testManager.setDataDiNascita(Date.valueOf(LocalDate.of(1999, 1, 1)));
        testManager.setStipendio(5000L);

        when(serviziUtenti.InsertManager(testManager))
            .thenReturn("Manager inserito con successo");

        Map<String, String> response = mainController.InsertManager(testManager);

        assertNotNull(response);
        assertEquals("Manager inserito con successo", response.get("message"));
    }

}
