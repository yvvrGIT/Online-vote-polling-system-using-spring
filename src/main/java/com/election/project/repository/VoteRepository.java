package com.election.project.repository;

import com.election.project.entity.User;
import com.election.project.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findByUser(User user);
}

