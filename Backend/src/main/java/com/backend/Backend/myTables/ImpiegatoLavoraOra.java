package com.backend.Backend.myTables;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ImpiegatoLavoraOra")
public class ImpiegatoLavoraOra {//associazione tra impiegato e ora lavorativa
    @EmbeddedId
    private ImpiegatoLavoraOraId id ;//chiave primaria composta
    @Enumerated(EnumType.STRING)//per salvare l'enum come stringa nel database
    @Column(nullable = false, name="tipooralavorativa")
    private TipoOra tipoOraLavorativa = TipoOra.NORMALE;
    public ImpiegatoLavoraOra(Impiegato impiegato, OraLavorativa oraLavorativa, TipoOra tipoOraLavorativa) {
        this.tipoOraLavorativa = tipoOraLavorativa;
        this.id = new ImpiegatoLavoraOraId(impiegato.getId(), oraLavorativa.getId());//creo la chiave primaria composta
    }
}
