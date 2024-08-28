package com.election.project.controllers.admin;

import com.election.project.entity.User;
import com.election.project.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {
    private UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin/dashboard";
    }

    @PostMapping("/admin/updateUserRole")
    public String updateUserRole(@RequestParam String username, @RequestParam String role) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setRole(role);
            userRepository.save(user);
        }
        return "redirect:/admin/dashboard";
    }
}
