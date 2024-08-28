package com.election.project.service;

import com.election.project.entity.User;
import com.election.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(1)
class AdminCheckService implements CommandLineRunner {
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public AdminCheckService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = userRepository.findByUsername("admin");
        if (user == null) {
            User temp = new User("admin", passwordEncoder.encode("admin"), "admin", "ADMIN");
            userRepository.save(temp);
        }
    }

}
