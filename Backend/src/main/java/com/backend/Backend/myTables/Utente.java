package com.backend.Backend.myTables;
import java.sql.Date;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data//getter e setter
@NoArgsConstructor//creazione del costruttore vuoto
@Entity//entità JPA
@Inheritance(strategy = InheritanceType.JOINED)//per utilizzare l'ereditarietà in JPA
@Table(name = "utente")//tabella del database

public abstract class Utente {
    @Id //chiave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)//generazione automatica dell'id
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private String dipartimento;

    @Column(nullable = false)
    private Date dataDiNascita;
    public Utente(String email, String password, String nome, String cognome, String telefono, String dipartimento, Date dataDiNascita) {
        this.email = email;
        this.setPassword(password);
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.dipartimento = dipartimento;
        this.dataDiNascita = dataDiNascita;
    }
    /*funzione relative all'hashing della password */
    public void setPassword(String rawPassword) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            this.password = passwordEncoder.encode(rawPassword);
        }
    public static String changePassword(String rawPassword) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            return passwordEncoder.encode(rawPassword);
        }
    public boolean verifyPassword(String rawPassword) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            return passwordEncoder.matches(rawPassword, this.password);
    }
}
