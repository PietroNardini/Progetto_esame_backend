package com.backend.Backend.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.backend.Backend.repositories.RepositoryPasswordResetToken;

@Service

public class ServiceTokenCleaner {

    @Autowired
    public RepositoryPasswordResetToken tokenRepository;

    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        tokenRepository.deleteAllExpiredSince(now);
        System.out.println("Expired tokens cleaned at " + now);
    }
}


