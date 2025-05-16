package com.backend.Backend.repositories.storico;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.backend.Backend.myTables.ImpiegatoLavoraOraId;
import com.backend.Backend.myTables.Storico.ImpiegatoLavoraOraStorico;

public interface ImpiegatoLavoraOraStoricoRepository extends JpaRepository<ImpiegatoLavoraOraStorico,ImpiegatoLavoraOraId>{
        @Transactional
        @Modifying
        void deleteById_IdOraLavorativaIn(List<Long> oraIds);
}
