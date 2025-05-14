package com.backend.Backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.Backend.myTables.ImpiegatoStipendiato;

public interface ImpiegatoStipendiatoRepository extends JpaRepository<ImpiegatoStipendiato, Long> {
        public List<ImpiegatoStipendiato> findByDipartimento(String dipartimento);//trova tutti gli impiegati stipendiati di un dipartimento
}
