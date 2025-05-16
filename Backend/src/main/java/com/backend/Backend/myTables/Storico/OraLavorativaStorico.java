package com.backend.Backend.myTables.Storico;

import java.sql.Date;
import java.time.LocalTime;

import com.backend.Backend.myTables.OraLavorativa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ora_lavorativa_storico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OraLavorativaStorico {
    @Id
    private Long id;
    @Column(nullable = false)
    private Date data;
    @Column(nullable = false)
    private LocalTime inizio;
    @Column(nullable = false)
    private LocalTime fine;
    public void fromOraLavorativa(OraLavorativa o){
            this.data=o.getData();
            this.fine=o.getFine();
            this.inizio=o.getInizio();
            this.id=o.getId();
    }
}
