package com.backend.Backend.myTables;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.Entity;

import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "impiegato")
@PrimaryKeyJoinColumn(name = "id")
@Inheritance(strategy = InheritanceType.JOINED)//per utilizzare l'ereditarietà in JPA
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, // Per sfruttare il polimorfismo devo inserire il tipo della classe nel json 
    include = JsonTypeInfo.As.PROPERTY, 
    property = "type" // il tipo della classe viene inserito come proprietà "type" nel json
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ImpiegatoPagatoOra.class, name = "pagatoOra"),//in base al tipo di impiegato viene smistato in un modo o nell'altro
    @JsonSubTypes.Type(value = ImpiegatoStipendiato.class, name = "stipendiato")
})
public abstract class Impiegato extends Utente {
    


}
