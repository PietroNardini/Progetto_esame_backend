package com.backend.Backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.Backend.myTables.ImpiegatoPagatoOra;

public interface ImpiegatoPagatoOraRepository extends JpaRepository<ImpiegatoPagatoOra, Long> {
    public List<ImpiegatoPagatoOra> findByDipartimento(String dipartimento);//trova tutti gli impiegati pagati a ore di un dipartimento
}
