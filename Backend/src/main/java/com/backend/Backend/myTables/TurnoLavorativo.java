package com.backend.Backend.myTables;

import jakarta.persistence.*;
import lombok.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;
import com.backend.Backend.myTables.TipoTurno;
@Entity
@Table(name = "turno_lavorativo",
       uniqueConstraints = @UniqueConstraint(columnNames = {"giorno", "tipo_turno"})) // impedisce duplicati
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // Use only explicitly included fields

public class TurnoLavorativo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_turno", nullable = false)
    @EqualsAndHashCode.Include
    private TipoTurno tipoTurno;

    @Column(name = "data", nullable = false)
    @EqualsAndHashCode.Include
    private LocalDate data;

    @OneToMany(mappedBy = "turnoLavorativo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OraLavorativa> oreLavorative;
    @ManyToMany(mappedBy = "turni")
    private Set<Impiegato> impiegati;
}

