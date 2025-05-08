package com.backend.Backend.myTables;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.Entity;

import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
    use = JsonTypeInfo.Id.NAME, // Per sfruttare il polimorfismo devo inserire il tipo della classe nel json 
    include = JsonTypeInfo.As.PROPERTY, 
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ImpiegatoPagatoOra.class, name = "pagatoOra"),//in base al tipo di impiegato viene serializzato in un modo o nell'altro
    @JsonSubTypes.Type(value = ImpiegatoStipendiato.class, name = "stipendiato")
})
public abstract class Impiegato extends Utente {
    @ManyToMany
    @JoinTable(
        name = "impiegato_turno",
        joinColumns = @JoinColumn(name = "impiegato_id"),
        inverseJoinColumns = @JoinColumn(name = "turno_lavorativo_id")
    )
    private Set<TurnoLavorativo> turni;


}
