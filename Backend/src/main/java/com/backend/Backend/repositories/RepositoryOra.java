package com.backend.Backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.Backend.myTables.OraLavorativa;

public interface RepositoryOra extends JpaRepository<OraLavorativa,Long>{
        
}
