package com.backend.Backend.repositories.storico;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.backend.Backend.myTables.Storico.OraLavorativaStorico;

public interface OraLavorativaStoricoRepository extends JpaRepository<OraLavorativaStorico,Long> {
        @Transactional
        @Modifying
        void deleteByDataBefore(Date data);
        List<OraLavorativaStorico> findByDataBefore(Date data);
}
