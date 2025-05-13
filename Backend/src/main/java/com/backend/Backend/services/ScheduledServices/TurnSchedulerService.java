package com.backend.Backend.services.ScheduledServices;

import com.backend.Backend.myTables.OraLavorativa;

import com.backend.Backend.repositories.RepositoryOra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TurnSchedulerService {

    @Autowired
    private RepositoryOra oraLavorativaRepository;
    // Eseguito il primo giorno del mese a mezzanotte
    @Scheduled(cron = "0 0 0 1 * ?")
    //@Scheduled(cron = "0 * * * * ?") // Per test: ogni minuto
    public void generateMonthlyWorkingHours() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
        LocalDate lastDayOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        List<OraLavorativa> allWorkingHours = new ArrayList<>();
        for (LocalDate date = firstDayOfMonth; !date.isAfter(lastDayOfMonth); date = date.plusDays(1)) {
            allWorkingHours.addAll(generateDailyWorkingHours(date));
        }
        oraLavorativaRepository.saveAll(allWorkingHours);
    }
    private List<OraLavorativa> generateDailyWorkingHours(LocalDate date) {
        List<OraLavorativa> dailyHours = new ArrayList<>();
        for (int i = 8; i < 24; i++) {
            LocalTime inizio = LocalTime.of(i, 0);
            LocalTime fine = (i < 23) ? LocalTime.of(i + 1, 0) : LocalTime.MIDNIGHT;
    
            dailyHours.add(buildOraLavorativa(date, inizio, fine));
        }
        return dailyHours;
    }

    private OraLavorativa buildOraLavorativa(LocalDate date, LocalTime start, LocalTime end) {
        OraLavorativa ora = new OraLavorativa();
        ora.setData(java.sql.Date.valueOf(date));
        ora.setInizio(start);
        ora.setFine(end);
        return ora;
    }
}
