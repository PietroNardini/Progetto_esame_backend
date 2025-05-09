package com.backend.Backend.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.Backend.myTables.OraLavorativa;

public interface RepositoryOra extends JpaRepository<OraLavorativa,Long>{

}
