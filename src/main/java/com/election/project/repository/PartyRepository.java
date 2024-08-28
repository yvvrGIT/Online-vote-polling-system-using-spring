package com.election.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.election.project.entity.Party;

import java.util.List;

public interface PartyRepository extends JpaRepository<Party, Long> {

    List<Party> findAll();

    Party findByName(String name);

    Party save(Party party);

    void deleteById(Long id);
}
