package com.backend.Backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import com.backend.Backend.myTables.Utente;

public interface UtentiRepository extends JpaRepository<Utente, Long> {//utente è il tipo che rappresenta la tabella e Long è il tipo della chiave primaria
        public Optional<Utente> findByEmail(String email); //metodo per trovare un utente in base alla sua email
        public Optional<Utente> findById(@NonNull Long id); //metodo per trovare un utente in base al suo id
        @Modifying
        @Transactional
        @Query("UPDATE Utente p SET p.password = :password WHERE p.email = :email")
        void setPassword(@Param("email") String email, @Param("password") String password);
}
