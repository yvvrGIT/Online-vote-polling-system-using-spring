package com.election.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.election.project.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    User findByUsername(String username);

    User save(User user);
}
