package com.backend.Backend.myTables;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalTime;
@Entity
@Table(
    name = "ora_lavorativa",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"data", "inizio", "fine"})
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor

@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Use only explicitly included fields

public class OraLavorativa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include // Include in hashCode and equals

    private Long id;

    @Column(nullable = false)
    @EqualsAndHashCode.Include // Include in hashCode and equals
    private Date data;

    @Column(nullable = false)
    @EqualsAndHashCode.Include // Include in hashCode and equals

    private LocalTime inizio;

    @Column(nullable = false)
    @EqualsAndHashCode.Include // Include in hashCode and equals

    private LocalTime fine;
   
   
}

