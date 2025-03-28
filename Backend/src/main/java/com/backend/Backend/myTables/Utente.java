package com.backend.Backend.myTables;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*Definisco la struttura della tabella dipendenti del database */
@Entity //indica che la classe rappresenta una tabella del database
@Table(name = "Utenti")//specifica il nome della tabella a cui l'entità è mappata
@Data //genera automaticamente getter,setter ,equals e hashCode
@AllArgsConstructor//È un'annotazione di Lombok che genera un costruttore con tutti i campi della classe come parametri.
@NoArgsConstructor//È un'annotazione di Lombok che genera un costruttore senza argomenti.
public class Utente {
    @Id //indica che il campo è la chiave primaria della tabella
    private String email;

}
