package com.backend.Backend.myTables.Storico;

import com.backend.Backend.myTables.ImpiegatoLavoraOra;
import com.backend.Backend.myTables.ImpiegatoLavoraOraId;
import com.backend.Backend.myTables.TipoOra;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "impiegato_lavora_ora_storico")
public class ImpiegatoLavoraOraStorico {
    @EmbeddedId
    private ImpiegatoLavoraOraId id ;//chiave primaria composta
    @Enumerated(EnumType.STRING)//per salvare l'enum come stringa nel database
    @Column(nullable = false, name="tipooralavorativa")
    private TipoOra tipoOraLavorativa = TipoOra.NORMALE;
    public ImpiegatoLavoraOraStorico(ImpiegatoLavoraOra io) {
        this.tipoOraLavorativa = io.getTipoOraLavorativa();
        this.id = new ImpiegatoLavoraOraId(
            io.getId().getIdImpiegato(),
            io.getId().getIdOraLavorativa()
        );
    }
    
}