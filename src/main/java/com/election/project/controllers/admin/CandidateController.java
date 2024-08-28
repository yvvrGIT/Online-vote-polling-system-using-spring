package com.election.project.controllers.admin;

import com.election.project.entity.Candidate;
import com.election.project.entity.Party;
import com.election.project.repository.CandidateRepository;
import com.election.project.repository.PartyRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CandidateController {
    private CandidateRepository candidateRepository;
    private PartyRepository partyRepository;

    public CandidateController(CandidateRepository candidateRepository, PartyRepository partyRepository) {
        this.candidateRepository = candidateRepository;
        this.partyRepository = partyRepository;
    }

    @GetMapping("/admin/candidates")
    public String listCandidates(Model model) {
        List<Candidate> candidates = candidateRepository.findAll();
        model.addAttribute("candidates", candidates);
        return "admin/list-candidates";
    }

    @GetMapping("/admin/add-candidate")
    public String addCandidate(Model model, Candidate candidate) {
        model.addAttribute("candidate", candidate);
        List<Party> parties = partyRepository.findAll();
        model.addAttribute("parties", parties);
        return "admin/add-candidate";
    }

    @PostMapping("/admin/add-candidate")
    public String addCandidatePost(@ModelAttribute("candidate") Candidate candidate, Model model) {
        Candidate temp = candidateRepository.findByName(candidate.getName());
        if (temp != null) {
            model.addAttribute("candidateExists", true);
            return "admin/add-candidate";
        }
        candidateRepository.save(candidate);
        return "redirect:/admin/candidates";
    }

    @PostMapping("/admin/delete-candidate")
    public String deleteCandidate(@RequestParam Long id) {
        candidateRepository.deleteById(id);
        return "redirect:/admin/candidates";
    }
}
