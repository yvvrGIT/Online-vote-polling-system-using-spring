package com.election.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.election.project.entity.User;
import com.election.project.service.AuthService;

@Controller
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String login(Model model, User user) {

        model.addAttribute("user", user);
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model, User user) {
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String registerSave(@ModelAttribute("user") User user, Model model) {
        User temp = authService.findByUsername(user.getUsername());
        if (temp != null) {
            model.addAttribute("Userexist", user);
            return "register";
        }
        authService.save(user);
        return "redirect:/register?success";
    }
}
