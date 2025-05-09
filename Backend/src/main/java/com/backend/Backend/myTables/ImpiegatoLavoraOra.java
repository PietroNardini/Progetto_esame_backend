package com.backend.Backend.myTables;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ImpiegatoLavoraOra")
public class ImpiegatoLavoraOra {
    @EmbeddedId
    private ImpiegatoLavoraOraId id ;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name="tipooralavorativa")
    private TipoOra tipoOraLavorativa = TipoOra.NORMALE;
    public ImpiegatoLavoraOra(Impiegato impiegato, OraLavorativa oraLavorativa, TipoOra tipoOraLavorativa) {
        this.tipoOraLavorativa = tipoOraLavorativa;
        this.id = new ImpiegatoLavoraOraId(impiegato.getId(), oraLavorativa.getId());
    }
}
