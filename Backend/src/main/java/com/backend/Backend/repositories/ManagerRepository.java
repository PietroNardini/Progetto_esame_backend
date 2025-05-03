package com.backend.Backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.Backend.myTables.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    

}
