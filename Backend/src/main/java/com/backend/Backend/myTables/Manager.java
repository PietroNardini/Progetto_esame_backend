package com.backend.Backend.myTables;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "manager")
@Data
@EqualsAndHashCode(callSuper = true)//per evitare conflitti di hashCode ed equals
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "id")//per unire le tabelle manager e utente tramite Eredità
public class Manager extends Utente {
    @Column(nullable = false)
    private Long stipendio;
    
}