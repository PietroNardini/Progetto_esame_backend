package com.backend.Backend.services.ScheduledServices;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.backend.Backend.repositories.RepositoryPasswordResetToken;

@Service

public class ServiceTokenCleaner {

    @Autowired
    public RepositoryPasswordResetToken tokenRepository;

    @Scheduled(cron = "0 0 2 * * ?") // Esegue ogni giorno alle 2:00 AM
    public void cleanExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        tokenRepository.deleteAllExpiredSince(now);//elimina tutti i token scaduti
        System.out.println("Expired tokens cleaned at " + now);
    }
}


