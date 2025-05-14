package com.backend.Backend.repositories;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.Backend.myTables.PasswordResetToken;

import jakarta.transaction.Transactional;


public interface RepositoryPasswordResetToken extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token); // Trova un token per il suo valore
    @Query("SELECT p FROM PasswordResetToken p WHERE p.user.email = :email")// Trova un token per l'email dell'utente
    PasswordResetToken findByEmail(@Param("email") String email); 
    void deleteByToken(String token); // Delete a token by its value
    boolean existsByToken(String token); // Check if a token exists by its value
    @Modifying
    @Transactional
    @Query("DELETE FROM PasswordResetToken t WHERE t.expiryDate < :now")//elemina i token scaduti
    void deleteAllExpiredSince(@Param("now") LocalDateTime now);
}
