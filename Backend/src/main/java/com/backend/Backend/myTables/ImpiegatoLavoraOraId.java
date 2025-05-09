package com.backend.Backend.myTables;

import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Data
@Embeddable
public class ImpiegatoLavoraOraId implements Serializable {
    private Long idImpiegato;
    private Long idOraLavorativa;
}