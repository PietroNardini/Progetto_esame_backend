package com.backend.Backend.myTables;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import com.backend.Backend.myTables.TipoOra;
@Entity
@Table(name = "ora_lavorativa")
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

    private LocalTime inizio;

    @Column(nullable = false)
    @EqualsAndHashCode.Include // Include in hashCode and equals

    private LocalTime fine;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @EqualsAndHashCode.Include // Include in hashCode and equals

    private TipoOra tipo = TipoOra.NORMALE;
    @ManyToOne
    @JoinColumn(name = "turno_lavorativo_id", nullable = false)
    private TurnoLavorativo turnoLavorativo;
}

