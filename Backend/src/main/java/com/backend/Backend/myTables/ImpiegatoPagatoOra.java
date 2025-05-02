package com.backend.Backend.myTables;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "impiegato_pagato_ora")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class ImpiegatoPagatoOra extends Impiegato {

    @Column(nullable = false)
    private Double pagaOraria;
    @Override
    public double calcolaStipendioMensile() {
        double stipendioMensile = 0.0;
        for (OraLavorativa oraLavorativa : getOreLavorateAlMese()) {
            if(oraLavorativa.getTipo().equals("straordinaria")) {
                stipendioMensile += 1.5 * pagaOraria; // Aumento del 50% per le ore straordinarie
            } else if (oraLavorativa.getTipo().equals("normale")) {
                stipendioMensile += pagaOraria;
            }
        }
        return stipendioMensile;
    }
}
