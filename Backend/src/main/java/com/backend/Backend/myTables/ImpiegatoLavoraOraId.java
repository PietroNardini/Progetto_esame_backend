package com.backend.Backend.myTables;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ImpiegatoLavoraOraId implements Serializable {
    private Long idImpiegato;
    private Long idOraLavorativa;
}