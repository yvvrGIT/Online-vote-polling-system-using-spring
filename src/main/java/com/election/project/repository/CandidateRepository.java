package com.election.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.election.project.entity.Candidate;

import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    List<Candidate> findAll();

    Candidate findByName(String name);

    Candidate save(Candidate candidate);

    void deleteById(Long id);
}

