package com.backend.Backend.myTables;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
@Entity
@Table(
    name = "ora_lavorativa",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"data", "inizio", "fine"}) //L'ora lavorativa è unica per combinazione di ogni data e intervallo di tempo
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor//costruttore con tutti i parametri

@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Per evitare conflitti di hashCode ed equals

public class OraLavorativa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include // Include in hashCode and equals

    private Long id;

    @Column(nullable = false)
    @EqualsAndHashCode.Include 
    private Date data;

    @Column(nullable = false)
    @EqualsAndHashCode.Include 

    private LocalTime inizio;

    @Column(nullable = false)
    @EqualsAndHashCode.Include 

    private LocalTime fine;
   
   public LocalDate getLocalDate() {
        return data.toLocalDate(); // Converte java.sql.Date in LocalDate
    }

   
}

