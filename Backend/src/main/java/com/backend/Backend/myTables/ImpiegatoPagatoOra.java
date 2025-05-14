package com.backend.Backend.myTables;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "impiegato_pagato_ora")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "id")//per unire le tabelle impiegato e utente tramite Eredità
public class ImpiegatoPagatoOra extends Impiegato {
    @Column(nullable = false)
    private Double pagaOraria;
    
}
