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
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ImpiegatoLavoraOra")
public class ImpiegatoLavoraOra {

    @EmbeddedId
    private ImpiegatoLavoraOraId id = new ImpiegatoLavoraOraId();

    @ManyToOne
    @MapsId("idImpiegato") 
    @JoinColumn(name = "idImpiegato", nullable = false)
    private Impiegato impiegato;

    @ManyToOne
    @MapsId("idOraLavorativa") 
    @JoinColumn(name = "idOraLavorativa", nullable = false)
    private OraLavorativa oraLavorativa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoOra tipoOraLavorativa = TipoOra.NORMALE;
}
