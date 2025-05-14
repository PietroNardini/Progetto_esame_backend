package com.backend.Backend.myTables;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "impiegato_stipendiato")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "id")//per unire le tabelle impiegato e utente tramite Eredità
public class ImpiegatoStipendiato extends Impiegato {
    @Column(nullable = false)
    private Double stipendioMensile;
    
}

