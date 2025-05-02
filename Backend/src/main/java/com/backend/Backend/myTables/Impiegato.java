package com.backend.Backend.myTables;

import java.util.Set;

import jakarta.persistence.Entity;

import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "impiegato")
@PrimaryKeyJoinColumn(name = "id")
@Inheritance(strategy = InheritanceType.JOINED)

public abstract class Impiegato extends Utente {
    @ManyToMany(mappedBy = "impiegati")
    private Set<OraLavorativa> oreLavorateAlMese;
    public abstract double calcolaStipendioMensile();

}
