package com.backend.Backend.services;

import com.backend.Backend.myTables.*;
import com.backend.Backend.repositories.RepositoryTurni;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class TurnSchedulerService {

    @Autowired
    private RepositoryTurni turnoLavorativoRepository;

    @Scheduled(cron = "0 0 0 1 * ?") // Esegui il metodo il primo giorno di ogni mese a mezzanotte
    //@Scheduled(cron = "0 * * * * ?") // Esegui il metodo ogni minuto (per testare)
    public void generateMonthlyTurns() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
        LocalDate lastDayOfMonth = today.withDayOfMonth(today.lengthOfMonth());

        Set<TurnoLavorativo> turns = new HashSet<>();
        for (LocalDate date = firstDayOfMonth; !date.isAfter(lastDayOfMonth); date = date.plusDays(1)) {
            for (TipoTurno tipoTurno : TipoTurno.values()) {// creo un turno mattina, pomeriggio e notte per ogni giorno del mese
                TurnoLavorativo turno = new TurnoLavorativo();
                turno.setData(date);
                turno.setTipoTurno(tipoTurno);
                turno.setOreLavorative(generateWorkingHours(turno));
                turns.add(turno);
            }
        }
        turnoLavorativoRepository.saveAll(turns);
    }

    private Set<OraLavorativa> generateWorkingHours(TurnoLavorativo turno) {
        Set<OraLavorativa> workingHours = new HashSet<>();

        LocalTime start, end;
        if (turno.getTipoTurno() == TipoTurno.MATTINA) {
            start = LocalTime.of(8, 0);
            end = LocalTime.of(12, 0);
        } else if (turno.getTipoTurno() == TipoTurno.POMERIGGIO) {
            start = LocalTime.of(13, 0);
            end = LocalTime.of(17, 0);
        } else {
            start = LocalTime.of(18, 0);
            end = LocalTime.of(22, 0);
        }

        OraLavorativa oraNormale = new OraLavorativa();
        oraNormale.setInizio(start);
        oraNormale.setFine(end);
        oraNormale.setTipo(TipoOra.NORMALE);
        oraNormale.setTurnoLavorativo(turno);

        workingHours.add(oraNormale);

        return workingHours;
    }
}