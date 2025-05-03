package com.backend.Backend.myTables;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "ora_lavorativa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OraLavorativa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String tipo; 
    @ManyToMany
    @JoinTable(
        name = "impiegato_ora_lavorativa",
        joinColumns = @JoinColumn(name = "ora_lavorativa_id"),
        inverseJoinColumns = @JoinColumn(name = "impiegato_id")
    )
    private Set<Impiegato> impiegati;
}

