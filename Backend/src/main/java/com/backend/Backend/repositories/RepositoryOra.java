package com.backend.Backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.Backend.myTables.OraLavorativa;

public interface RepositoryOra extends JpaRepository<OraLavorativa,Long>{

}
