package com.backend.Backend.myTables;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

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
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, // Use a type identifier in the JSON
    include = JsonTypeInfo.As.PROPERTY, // Include the type identifier as a property
    property = "type" // The property name in the JSON that specifies the type
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ImpiegatoPagatoOra.class, name = "pagatoOra"),
    @JsonSubTypes.Type(value = ImpiegatoStipendiato.class, name = "stipendiato")
})
public abstract class Impiegato extends Utente {
    @ManyToMany(mappedBy = "impiegati")
    private Set<OraLavorativa> oreLavorateAlMese;
    public abstract double calcolaStipendioMensile();

}
