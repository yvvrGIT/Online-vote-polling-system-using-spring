package com.election.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.election.project.entity.Election;

import java.util.List;

public interface ElectionRepository extends JpaRepository<Election, Long> {
    List<Election> findByStatus(Election.ElectionStatus status);
}

