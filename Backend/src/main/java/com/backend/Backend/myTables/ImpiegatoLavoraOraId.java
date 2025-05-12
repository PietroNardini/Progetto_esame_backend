package com.backend.Backend.myTables;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ImpiegatoLavoraOraId implements Serializable {
    private Long idImpiegato;
    private Long idOraLavorativa;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImpiegatoLavoraOraId)) return false;
        ImpiegatoLavoraOraId that = (ImpiegatoLavoraOraId) o;
        return Objects.equals(idImpiegato, that.idImpiegato) &&
               Objects.equals(idOraLavorativa, that.idOraLavorativa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idImpiegato, idOraLavorativa);
    }
}