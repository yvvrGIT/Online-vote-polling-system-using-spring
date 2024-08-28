package com.election.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.election.project.entity.User;
import com.election.project.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    PasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {
        User temp = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()),
                user.getFullname(), "USER");
        return userRepository.save(temp);
    }

}
