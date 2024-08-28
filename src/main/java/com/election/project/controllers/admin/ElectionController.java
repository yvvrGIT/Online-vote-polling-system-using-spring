package com.election.project.controllers.admin;

import com.election.project.entity.Election;
import com.election.project.repository.ElectionRepository;
import com.election.project.service.ElectionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ElectionController {
    private ElectionRepository electionRepository;
    private ElectionService electionService;

    public ElectionController(ElectionRepository electionRepository, ElectionService electionService) {
        this.electionRepository = electionRepository;
        this.electionService = electionService;
    }

    @GetMapping("/admin/elections")
    public String listElections(@RequestParam(name = "error", required = false) String error, Model model) {
        List<Election> elections = electionRepository.findAll();
        model.addAttribute("elections", elections);
        model.addAttribute("active", Election.ElectionStatus.ACTIVE);

        if (error != null) {
            model.addAttribute("error", error);
        }

        return "admin/list-elections";
    }

    @GetMapping("/admin/add-election")
    public String addElection() {
        List<Election> temp = electionRepository.findByStatus(Election.ElectionStatus.ACTIVE);
        if (!temp.isEmpty()) {
            return "redirect:/admin/elections?error=Active election exists";
        }

        electionService.startElection();
        return "redirect:/admin/elections";
    }

    @PostMapping("/admin/end-election")
    public String endElection(@RequestParam(name = "id") Long id) {
        electionService.endElection(id);
        return "redirect:/admin/elections";
    }
}
