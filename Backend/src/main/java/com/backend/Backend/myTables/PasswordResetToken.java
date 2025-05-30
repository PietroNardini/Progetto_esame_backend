package com.backend.Backend.myTables;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@Table(name = "password_reset_token")//specifica il nome della tabella a cui l'entità è mappata

@AllArgsConstructor//È un'annotazione di Lombok che genera un costruttore con tutti i campi della classe come parametri.
@NoArgsConstructor//È un'annotazione di Lombok che genera un costruttore senza argomenti.
public class PasswordResetToken {
 
    public static final int EXPIRATION = 60*24 ;// 60 minuti * 24 ore = 1 giorno
    // Definisce la durata di validità del token in minuti
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    private String token;// Token di reset della password (parte del link mandato via email)
 
    @OneToOne(targetEntity = Utente.class, fetch = FetchType.EAGER)//relazione uno a uno con l'utente
    // FetchType.EAGER significa che l'oggetto Utente associato sarà caricato immediatamente quando viene caricato il token
    @JoinColumn(nullable = false, name = "user_id")
    private Utente user;
 
    private LocalDateTime expiryDate;
    public PasswordResetToken(String token, Utente user) {
        this.token = token;
        this.user = user;
        this.expiryDate = LocalDateTime.now().plusMinutes(EXPIRATION); // Set expiry date to 60 minutes from now
    }

    public void setExpiryDate() {
        this.expiryDate = LocalDateTime.now().plusMinutes(EXPIRATION); // Update expiry date to 60 minutes from now
    }
    
}